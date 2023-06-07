import 'dart:math';

import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_rating_bar/flutter_rating_bar.dart';
import 'package:front/auth/auth_bloc.dart';
import 'package:front/login/login_screen.dart';
import 'package:front/model/plato_detail_result.dart';
import 'package:front/platodetail/platodetail_bloc.dart';

const String imgBase = "http://localhost:8080/download/";

class PlatoScreen extends StatelessWidget {
  PlatoScreen({super.key, required this.platoId});

  String platoId;

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (context) => PlatodetailBloc()..add(PlatoFetched(platoId)),
      child: PlatoUI(),
    );
  }
}

class PlatoUI extends StatefulWidget {
  @override
  State<PlatoUI> createState() => _PlatoUIState();
}

class _PlatoUIState extends State<PlatoUI> {
  final GlobalKey<ScaffoldState> _scaffoldKey = new GlobalKey<ScaffoldState>();
  final GlobalKey<FormState> _formKey = GlobalKey<FormState>();
  double rating = 2.5;
  final comentarioController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return BlocBuilder<PlatodetailBloc, PlatodetailState>(
      builder: (context, state) {
        switch (state.status) {
          case PlatodetailStatus.failure:
            return const Center(child: Text('Fallo al cargar el plato'));
          case PlatodetailStatus.success:
            return Scaffold(
                key: _scaffoldKey,
                appBar: AppBar(
                  leading: BackButton(),
                  automaticallyImplyLeading: false,
                  title: Text(state.plato!.nombre!),
                  backgroundColor: Theme.of(context).colorScheme.onSecondary,
                  foregroundColor: Theme.of(context).colorScheme.primary,
                ),
                drawer: Drawer(
                  // Add a ListView to the drawer. This ensures the user can scroll
                  // through the options in the drawer if there isn't enough vertical
                  // space to fit everything.
                  child: ListView(
                    // Important: Remove any padding from the ListView.
                    padding: EdgeInsets.zero,
                    children: [
                      Form(
                        key: _formKey,
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: <Widget>[
                            Padding(
                              padding: EdgeInsets.only(
                                  bottom: 5, top: 40, left: 20, right: 20),
                              child: Center(
                                child: RatingBar.builder(
                                  initialRating: 3,
                                  minRating: 1,
                                  direction: Axis.horizontal,
                                  allowHalfRating: true,
                                  itemCount: 5,
                                  itemPadding:
                                      EdgeInsets.symmetric(horizontal: 4.0),
                                  itemBuilder: (context, _) => Icon(
                                    Icons.star,
                                    color: Colors.red.shade700,
                                  ),
                                  onRatingUpdate: (newRating) {
                                    rating = newRating;
                                  },
                                ),
                              ),
                            ),
                            Padding(
                              padding: EdgeInsets.all(20),
                              child: TextFormField(
                                keyboardType: TextInputType.multiline,
                                maxLines: null,
                                controller: comentarioController,
                                decoration: const InputDecoration(
                                  hintText: 'Dinos tu opinión',
                                  labelText: 'Comentario',
                                ),
                              ),
                            ),
                            Padding(
                                padding:
                                    const EdgeInsets.symmetric(vertical: 16.0),
                                child: Center(
                                  child: ElevatedButton(
                                    onPressed: () {
                                      context.read<PlatodetailBloc>().add(
                                          RateEvent(state.id, rating,
                                              comentarioController.text));
                                      _scaffoldKey.currentState!.closeDrawer();
                                    },
                                    child: const Text('Submit'),
                                  ),
                                )),
                          ],
                        ),
                      ),
                    ],
                  ),
                ),
                body: SingleChildScrollView(
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    mainAxisSize: MainAxisSize.min,
                    verticalDirection: VerticalDirection.down,
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Center(
                        child: Image.network(
                          imgBase + state.plato!.imgUrl!,
                          height: 300,
                          fit: BoxFit.cover,
                        ),
                      ),
                      Padding(
                        padding: EdgeInsets.all(15),
                        child: Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Text(
                              state.plato!.nombre!,
                              style: TextStyle(
                                  fontWeight: FontWeight.w600, fontSize: 15),
                            ),
                            Text(
                              "${state.plato!.precio!} €",
                              style: TextStyle(
                                  fontWeight: FontWeight.w800, fontSize: 15),
                            )
                          ],
                        ),
                      ),
                      Padding(
                          padding: EdgeInsets.only(left: 15, right: 15, top: 5),
                          child: Text(
                            state.plato!.descripcion!,
                            style: TextStyle(
                                fontWeight: FontWeight.w300, fontSize: 12),
                          )),
                      Padding(
                          padding: EdgeInsets.all(15),
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Text(
                                "Ingredientes",
                                style: TextStyle(fontWeight: FontWeight.w600),
                              ),
                              Wrap(
                                  children: List.generate(
                                state.plato!.ingredientes!.length,
                                (index) => Container(
                                    margin: EdgeInsets.only(right: 4, top: 4),
                                    child: Material(
                                      elevation: 2,
                                      child: Container(
                                          padding: EdgeInsets.all(3),
                                          child: Text(state
                                              .plato!.ingredientes![index])),
                                    )),
                              ))
                            ],
                          )),
                      Padding(
                          padding: EdgeInsets.only(
                              left: 15, right: 15, top: 15, bottom: 5),
                          child: Row(
                            mainAxisAlignment: MainAxisAlignment.spaceBetween,
                            children: [
                              Text(
                                "Valoraciones",
                                style: TextStyle(fontWeight: FontWeight.w600),
                              ),
                              if (state.plato!.valoracionMedia != null)
                                if (!state.plato!.valoracionMedia!.isNaN)
                                  Text.rich(
                                    TextSpan(
                                      children: [
                                        TextSpan(
                                          text:
                                              "${roundDouble(state.plato!.valoracionMedia!, 2)}",
                                          style: TextStyle(
                                              fontWeight: FontWeight.w600),
                                        ),
                                        WidgetSpan(
                                            child: Icon(Icons.star_rounded,
                                                size: 17)),
                                      ],
                                    ),
                                  ),
                            ],
                          )),
                      if (state.plato!.valoraciones != null)
                        BlocBuilder<AuthenticationBloc, AuthenticationState>(
                          builder: (context, authState) {
                            if (authState is AuthenticationAuthenticated) {
                              if (!state.plato!.valoraciones!
                                  .map((v) => v.username)
                                  .contains(authState.user.email)) {
                                return Center(
                                  child: ElevatedButton(
                                      onPressed: () => _scaffoldKey
                                          .currentState!
                                          .openDrawer(),
                                      child: Text("Valorar")),
                                );
                              }
                            }
                            return SizedBox(
                              height: 0,
                            );
                          },
                        ),
                      if (state.plato!.valoraciones != null)
                        Padding(
                          padding: EdgeInsets.all(15),
                          child: ListView.builder(
                            shrinkWrap: true,
                            itemBuilder: (BuildContext context, int index) {
                              return ReviewItem(
                                  valoracion:
                                      state.plato!.valoraciones![index]);
                            },
                            itemCount: state.plato!.valoraciones!.length,
                          ),
                        ),
                      if (state.plato!.valoraciones == null)
                        Center(
                          child: Column(
                            children: [
                              Container(
                                margin: EdgeInsets.all(5),
                                child: Text(
                                  "Se el primero en dejar tu opinión.",
                                  style: TextStyle(
                                      fontWeight: FontWeight.w300,
                                      fontSize: 12),
                                ),
                              ),
                              BlocBuilder<AuthenticationBloc,
                                  AuthenticationState>(
                                builder: (context, state) {
                                  if (state is AuthenticationAuthenticated) {
                                    return ElevatedButton(
                                        onPressed: () => _scaffoldKey
                                            .currentState!
                                            .openDrawer(),
                                        child: Text("Valorar"));
                                  } else if (state
                                      is AuthenticationNotAuthenticated) {
                                    return ElevatedButton(
                                        onPressed: () => Navigator.push(
                                            context,
                                            MaterialPageRoute(
                                                builder: (context) =>
                                                    LoginScreen())),
                                        child: Text("Loggeate para valorar."));
                                  } else {
                                    return Text("We made a fucky wucky");
                                  }
                                },
                              ),
                            ],
                          ),
                        )
                    ],
                  ),
                ));
          case PlatodetailStatus.initial:
            return CircularProgressIndicator();
        }
      },
    );
  }

  double roundDouble(double value, int places) {
    num mod = pow(10.0, places);
    return ((value * mod).round().toDouble() / mod);
  }
}

class ReviewItem extends StatelessWidget {
  const ReviewItem({super.key, required this.valoracion});

  final Valoracion valoracion;

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: EdgeInsets.only(bottom: 5),
      child: Material(
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(10.0),
        ),
        elevation: 1,
        child: Flexible(
          child: ListTile(
            leading: Text("${valoracion.nota}"),
            title: Text(valoracion.username!),
            subtitle: Text(valoracion.comentario!),
            leadingAndTrailingTextStyle: TextStyle(
                fontWeight: FontWeight.w800, fontSize: 20, color: Colors.black),
          ),
        ),
      ),
    );
  }
}
