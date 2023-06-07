import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:front/model/plato_list_result.dart';
import 'package:front/model/restaurante_detail.dart';
import 'package:front/restaurantmenu/restaurant_menu_bloc.dart';

import '../landing/landing_bloc.dart';
import '../landing/landing_screen.dart';
import '../platodetail/plato_detail_screen.dart';

const String imgBase = "http://localhost:8080/download/";
const String imgBasePlato = "http://localhost:8080/download/";

class RestaurantScreen extends StatelessWidget {
  RestaurantScreen({super.key, required this.restaurantId});

  String restaurantId;

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (context) =>
          RestaurantMenuBloc()..add(RestaurantFetched(restaurantId)),
      child: RestaurantUI(),
    );
  }
}

class RestaurantUI extends StatefulWidget {
  @override
  State<RestaurantUI> createState() => _RestaurantUIState();
}

class _RestaurantUIState extends State<RestaurantUI> {
  final _scrollController = ScrollController();
  bool showFilters = false;
  final GlobalKey<FormState> _formKey = GlobalKey<FormState>();
  double initMin = 0.1;
  double initMax = 30.0;
  double? _minPriceValue = null;
  double? _maxPriceValue = null;
  bool? _noGluten = null;
  String? _busqueda = null;

  @override
  void initState() {
    super.initState();
    _scrollController.addListener(_onScroll);
  }

  @override
  Widget build(BuildContext context) {
    return BlocBuilder<RestaurantMenuBloc, RestaurantMenuState>(
      builder: (context, state) {
        switch (state.status) {
          case RestaurantMenuStatus.failure:
            return const Center(child: Text('Fallo al cargar el restaurante'));
          case RestaurantMenuStatus.success:
            return buildScreen(state);
          case RestaurantMenuStatus.initial:
            return const Center(child: CircularProgressIndicator());
          case RestaurantMenuStatus.noneFound:
            return buildScreen(state);
          case RestaurantMenuStatus.searchFound:
            return buildScreen(state);
        }
      },
    );
  }

  Widget buildScreen(RestaurantMenuState state) {
    return Scaffold(
      appBar: AppBar(
        title: Text(state.restaurante!.nombre!),
        backgroundColor: Theme.of(context).colorScheme.onSecondary,
        foregroundColor: Theme.of(context).colorScheme.primary,
      ),
      body: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          mainAxisSize: MainAxisSize.min,
          verticalDirection: VerticalDirection.down,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Image.network(
              imgBase + state.restaurante!.coverImgUrl!,
            ),
            Padding(
                padding: EdgeInsets.all(15),
                child: Column(children: [
                  Text(
                    state.restaurante!.nombre!,
                    style: TextStyle(fontWeight: FontWeight.w700),
                  ),
                  SizedBox(
                    height: 2,
                  ),
                  Text(
                    state.restaurante!.descripcion!,
                    style: TextStyle(fontWeight: FontWeight.w300, fontSize: 12),
                  )
                ])),
            Padding(
              padding: EdgeInsets.only(left: 20, right: 20),
              child: Row(
                children: [
                  Expanded(
                    child: Divider(),
                  ),
                  IconButton(
                      onPressed: () {
                        setState(() {
                          showFilters = !showFilters;
                        });
                      },
                      icon: Icon(Icons.filter_list))
                ],
              ),
            ),
            if (showFilters)
              Padding(
                padding: EdgeInsets.all(20),
                child: Container(
                  child: Form(
                    key: _formKey,
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: <Widget>[
                        ListTile(
                            dense: true,
                            visualDensity: VisualDensity(vertical: -3),
                            title: TextFormField(
                              decoration: const InputDecoration(
                                hintText: 'Hamburguesa con queso...',
                              ),
                              onChanged: (String value) {
                                setState(() {
                                  _busqueda = value;
                                });
                              },
                            ),
                            leading: Text("Producto")),
                        ListTile(
                            dense: true,
                            visualDensity: VisualDensity(vertical: -3),
                            title: Slider(
                              activeColor: Colors.red.shade800,
                              inactiveColor: Colors.red.shade700,
                              min: 0.0,
                              max: _maxPriceValue ?? 30.0,
                              value: _minPriceValue ?? initMin,
                              label: _minPriceValue != null
                                  ? _minPriceValue!.round().toString()
                                  : "",
                              onChanged: (double value) {
                                setState(() {
                                  _minPriceValue = value;
                                });
                              },
                            ),
                            leading: Text("Precio min")),
                        ListTile(
                            dense: true,
                            visualDensity:
                                VisualDensity(vertical: -3, horizontal: -3),
                            title: Slider(
                              activeColor: Colors.red.shade800,
                              inactiveColor: Colors.red.shade700,
                              min: _minPriceValue ?? 0.0,
                              max: 30.0,
                              value: _maxPriceValue ?? initMax,
                              label: _maxPriceValue != null
                                  ? _maxPriceValue!.round().toString()
                                  : "",
                              onChanged: (double value) {
                                setState(() {
                                  _maxPriceValue = value;
                                });
                              },
                            ),
                            leading: Text("Precio max")),
                        ListTile(
                          dense: true,
                          visualDensity:
                              VisualDensity(vertical: -3, horizontal: -3),
                          title: const Text('Sin gluten'),
                          leading: Checkbox(
                            value: _noGluten != null ? _noGluten! : false,
                            onChanged: (value) {
                              setState(() {
                                _noGluten = value!;
                              });
                            },
                          ),
                        ),
                        Padding(
                          padding: const EdgeInsets.symmetric(vertical: 16.0),
                          child: ElevatedButton(
                            onPressed: () {
                              context.read<RestaurantMenuBloc>().add(
                                  SearchPlatosEvent(_busqueda, _minPriceValue,
                                      _maxPriceValue, _noGluten));
                              setState(() {
                                showFilters = !showFilters;
                                _minPriceValue = null;
                                _maxPriceValue = null;
                                _noGluten = null;
                                _busqueda = null;
                              });
                            },
                            child: const Text('Buscar'),
                          ),
                        ),
                      ],
                    ),
                  ),
                ),
              ),
            if (state.status == RestaurantMenuStatus.noneFound)
              Expanded(
                  child: Center(
                      child: Text(
                          "Parece que no hemos encontrado ningún plato."))),
            if (state.status == RestaurantMenuStatus.success ||
                state.status == RestaurantMenuStatus.searchFound)
              Expanded(
                  child: ListView.builder(
                shrinkWrap: true,
                itemBuilder: (BuildContext context, int index) {
                  return index >= state.platos.length
                      ? const BottomLoader()
                      : PlatoItem(plato: state.platos[index]);
                },
                itemCount: state.hasReachedMax
                    ? state.platos.length
                    : state.platos.length + 1,
                controller: _scrollController,
              ))
          ]),
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
    if (_isBottom) context.read<RestaurantMenuBloc>().add(NextPlatosFetched());
  }

  bool get _isBottom {
    if (!_scrollController.hasClients) return false;
    final maxScroll = _scrollController.position.maxScrollExtent;
    final currentScroll = _scrollController.offset;
    return currentScroll >= (maxScroll * 0.9);
  }
}

class PlatoItem extends StatelessWidget {
  const PlatoItem({super.key, required this.plato});

  final PlatoGeneric plato;

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: EdgeInsets.only(left: 5, right: 5),
      child: Container(
        height: 70,
        child: ListTile(
          leading: Image.network(
            imgBasePlato + plato.imgUrl!,
            fit: BoxFit.contain,
          ),
          title: Text(plato.nombre!),
          subtitle: Text("${plato.precio!} €"),
          trailing: IconButton(
            icon: Icon(Icons.arrow_right_rounded),
            onPressed: () => Navigator.push(
                context,
                MaterialPageRoute(
                    builder: (context) => PlatoScreen(
                          platoId: plato.id!,
                        ))),
          ),
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(10.0),
          ),
        ),
      ),
    );
  }
}
