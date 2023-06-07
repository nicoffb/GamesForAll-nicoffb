import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:front/landing/landing_bloc.dart';
import 'package:front/model/RestauranteListResult.dart';
import 'package:front/restaurantmenu/restaurant_screen.dart';

import '../auth/auth_bloc.dart';

const String imgBase = "http://localhost:8080/download/";

final TextEditingController controller = new TextEditingController();

class LandingScreen extends StatelessWidget {
  const LandingScreen({super.key});

  @override
  Widget build(BuildContext context) {
    int _selectedIndex = 1;
    return BlocProvider(
      create: (context) => LandingBloc()..add(RestaurantsFetched()),
      child: MainBody(),
    );
  }
}

class MainBody extends StatefulWidget {
  const MainBody({super.key});

  @override
  State<MainBody> createState() => _MainBodyState();
}

class _MainBodyState extends State<MainBody> {
  final _scrollController = ScrollController();

  @override
  void initState() {
    super.initState();
    _scrollController.addListener(_onScroll);
  }

  @override
  Widget build(BuildContext context) {
    return BlocBuilder<LandingBloc, LandingState>(
      builder: (context, state) {
        switch (state.status) {
          case LandingStatus.failure:
            return Center(
                child: Column(
              children: [
                Text('Fallo al cargar los restaurantes'),
                ElevatedButton(
                  onPressed: () {
                    context.read<AuthenticationBloc>().add(UserLoggedOut());
                    context.read<LandingBloc>().add(RestaurantsFetched());
                  },
                  child: Text("Reintentar"),
                )
              ],
            ));
          case LandingStatus.success:
            if (state.restaurantes.isEmpty) {
              return const Center(child: Text('No se encuentran restaurantes'));
            }
            return ListView.builder(
              itemBuilder: (BuildContext context, int index) {
                return index >= state.restaurantes.length
                    ? const BottomLoader()
                    : RestauranteItem(restaurante: state.restaurantes[index]);
              },
              itemCount: state.hasReachedMax
                  ? state.restaurantes.length
                  : state.restaurantes.length + 1,
              controller: _scrollController,
            );
          case LandingStatus.initial:
            return const Center(child: CircularProgressIndicator());
        }
      },
    );
  }

  @override
  void dispose() {
    _scrollController
      ..removeListener(_onScroll)
      ..dispose();
    super.dispose();
  }

  void _onScroll() {
    if (_isBottom) context.read<LandingBloc>().add(RestaurantsFetched());
  }

  bool get _isBottom {
    if (!_scrollController.hasClients) return false;
    final maxScroll = _scrollController.position.maxScrollExtent;
    final currentScroll = _scrollController.offset;
    return currentScroll >= (maxScroll * 0.9);
  }
}

class BottomLoader extends StatelessWidget {
  const BottomLoader({super.key});

  @override
  Widget build(BuildContext context) {
    return const Center(
        child: Padding(
      padding: EdgeInsets.only(top: 6, bottom: 6),
      child: SizedBox(
        height: 24,
        width: 24,
        child: CircularProgressIndicator(strokeWidth: 1.5),
      ),
    ));
  }
}

class RestauranteItem extends StatelessWidget {
  const RestauranteItem({super.key, required this.restaurante});

  final RestauranteGeneric restaurante;

  @override
  Widget build(BuildContext context) {
    final textTheme = Theme.of(context).textTheme;
    return Material(
        child: GestureDetector(
            child: Container(
              height: 200,
              child: Card(
                semanticContainer: true,
                clipBehavior: Clip.antiAliasWithSaveLayer,
                child: Column(
                    crossAxisAlignment: CrossAxisAlignment.stretch,
                    children: [
                      Expanded(
                        child: Image.network(
                          imgBase + restaurante.coverImgUrl!,
                          fit: BoxFit.fill,
                        ),
                        flex: 7,
                      ),
                      Padding(
                        padding: EdgeInsets.all(8),
                        child: Text(restaurante.nombre!,
                            style: TextStyle(fontWeight: FontWeight.w600)),
                      ),
                    ]),
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(10.0),
                ),
                elevation: 5,
                margin: EdgeInsets.all(10),
              ),
            ),
            onTap: () => Navigator.push(
                context,
                MaterialPageRoute(
                    builder: (context) => RestaurantScreen(
                          restaurantId: restaurante.id!,
                        )))));
  }
}
