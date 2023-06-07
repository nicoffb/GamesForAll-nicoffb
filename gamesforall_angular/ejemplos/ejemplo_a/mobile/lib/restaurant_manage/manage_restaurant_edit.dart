import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:front/model/restaurante_detail.dart';

import '../model/restaurante_request.dart';
import 'manage_restaurant_bloc.dart';
import 'manage_restaurant_event.dart';

class RestaurantEditForm extends StatelessWidget {
  RestaurantEditForm({super.key, required this.restaurant});

  RestauranteDetailResult restaurant;

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (context) => ManageRestaurantBloc(),
      child: RestaurantEditFormUI(restaurant),
    );
  }
}

class RestaurantEditFormUI extends StatefulWidget {
  RestaurantEditFormUI(this.restaurant);

  RestauranteDetailResult restaurant;

  @override
  State<RestaurantEditFormUI> createState() =>
      _RestaurantEditFormUIUIState(restaurant);
}

class _RestaurantEditFormUIUIState extends State<RestaurantEditFormUI> {
  RestauranteDetailResult restaurant;
  _RestaurantEditFormUIUIState(this.restaurant);
  TextEditingController _nombreController = TextEditingController();
  TextEditingController _descripcionController = TextEditingController();
  TimeOfDay _apertura = TimeOfDay.now();
  TimeOfDay _cierre = TimeOfDay.now();

  @override
  void initState() {
    _nombreController.text = restaurant.nombre!;
    _descripcionController.text = restaurant.descripcion!;
    _apertura = restaurant.apertura!;
    _cierre = restaurant.cierre!;
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return BlocConsumer<ManageRestaurantBloc, ManageRestaurantState>(
      listenWhen: (previous, current) {
        return current.status == ManageRestaurantStatus.failure;
      },
      listener: (context, state) {
        ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(content: Text("Fallo al editar el estaurante")));
      },
      buildWhen: (previous, current) {
        return current.status != ManageRestaurantStatus.failure;
      },
      builder: (context, state) {
        if (state.status == ManageRestaurantStatus.editSuccess) {
          Navigator.of(context).pop(true);
        }
        if (state.status == ManageRestaurantStatus.success ||
            state.status == ManageRestaurantStatus.initial)
          return Scaffold(
              appBar: AppBar(
                title: Text("Editar ${restaurant.nombre}"),
                backgroundColor: Theme.of(context).colorScheme.onSecondary,
                foregroundColor: Theme.of(context).colorScheme.primary,
              ),
              body: Form(
                  child: Padding(
                padding: EdgeInsets.all(25),
                child: Column(
                  children: <Widget>[
                    Row(
                      children: [
                        Expanded(
                          flex: 4,
                          child: Text("Nombre"),
                        ),
                        Expanded(
                          flex: 10,
                          child: TextFormField(
                            controller: _nombreController,
                          ),
                        )
                      ],
                    ),
                    SizedBox(
                      height: 10,
                    ),
                    Row(
                      children: [
                        Expanded(
                          flex: 4,
                          child: Text("Descripción"),
                        ),
                        Expanded(
                          flex: 10,
                          child: TextFormField(
                            minLines: 2,
                            maxLines: 5,
                            controller: _descripcionController,
                          ),
                        )
                      ],
                    ),
                    SizedBox(
                      height: 10,
                    ),
                    Row(
                      children: [
                        Expanded(
                          flex: 4,
                          child: Text("Apertura"),
                        ),
                        Expanded(
                            flex: 10,
                            child: ElevatedButton(
                                onPressed: () {
                                  showTimePicker(
                                          initialEntryMode:
                                              TimePickerEntryMode.input,
                                          context: context,
                                          initialTime: _apertura)
                                      .then((value) => {
                                            this.setState(() {
                                              _apertura = value ?? _apertura;
                                            })
                                          });
                                },
                                child: Text(
                                    "${_apertura.hour}:${_apertura.minute}")))
                      ],
                    ),
                    SizedBox(
                      height: 10,
                    ),
                    Row(
                      children: [
                        Expanded(
                          flex: 4,
                          child: Text("Cierre"),
                        ),
                        Expanded(
                            flex: 10,
                            child: ElevatedButton(
                                onPressed: () {
                                  showTimePicker(
                                          initialEntryMode:
                                              TimePickerEntryMode.input,
                                          context: context,
                                          initialTime: _cierre)
                                      .then((value) => {
                                            this.setState(() {
                                              _cierre = value ?? _cierre;
                                            })
                                          });
                                },
                                child:
                                    Text("${_cierre.hour}:${_cierre.minute}")))
                      ],
                    ),
                    SizedBox(
                      height: 10,
                    ),
                    ElevatedButton(
                      onPressed: () {
                        context.read<ManageRestaurantBloc>().add(EditRestaurant(
                            restaurant.id!,
                            RestauranteEditRequest(
                                _nombreController.value.text,
                                "${_apertura.hour < 10 ? "0${_apertura.hour}" : _apertura.hour}:${_apertura.minute < 10 ? "0${_apertura.minute}" : _apertura.minute}:00",
                                "${_cierre.hour < 10 ? "0${_cierre.hour}" : _cierre.hour}:${_cierre.minute < 10 ? "0${_cierre.minute}" : _cierre.minute}:00",
                                _descripcionController.value.text)));
                      },
                      child: const Text('Submit'),
                    ),
                  ],
                ),
              )));
        return Scaffold(
          body: Center(
            child: CircularProgressIndicator(),
          ),
        );
      },
    );
  }
}
