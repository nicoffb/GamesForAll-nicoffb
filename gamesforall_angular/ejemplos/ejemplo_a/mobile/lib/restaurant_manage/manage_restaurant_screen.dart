import 'dart:async';
import 'dart:io';

import 'package:file_picker/file_picker.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:front/model/restaurante_detail.dart';
import 'package:front/restaurant_form/restaurant_form_screen.dart';
import '../home_screen.dart';
import '../model/plato_request.dart';
import '../platos_manage/manage_platos_screen.dart';
import 'manage_restaurant_bloc.dart';
import 'manage_restaurant_edit.dart';
import 'manage_restaurant_event.dart';

String imgBase = "http://localhost:8080/download/";

class ManageRestaurantScreen extends StatelessWidget {
  ManageRestaurantScreen({super.key, required this.restaurantId});

  String restaurantId;

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (context) =>
          ManageRestaurantBloc()..add(RestaurantFetched(restaurantId)),
      child: ManageRestaurantUI(),
    );
  }
}

class ManageRestaurantUI extends StatefulWidget {
  @override
  State<ManageRestaurantUI> createState() => _ManageRestaurantUIState();
}

class _ManageRestaurantUIState extends State<ManageRestaurantUI> {
  @override
  Widget build(BuildContext context) {
    return BlocConsumer<ManageRestaurantBloc, ManageRestaurantState>(
      listenWhen: (previous, current) {
        return current.status == ManageRestaurantStatus.deleteFailure ||
            current.status == ManageRestaurantStatus.editSuccess ||
            current.status == ManageRestaurantStatus.createPlatoSuccess;
      },
      listener: (context, state) {
        if (state.status == ManageRestaurantStatus.deleteFailure)
          _showDeleteError();
        if (state.status == ManageRestaurantStatus.editSuccess)
          ScaffoldMessenger.of(context).showSnackBar(
            const SnackBar(content: Text('Modificado con éxito')),
          );
        if (state.status == ManageRestaurantStatus.createPlatoSuccess)
          ScaffoldMessenger.of(context).showSnackBar(
            const SnackBar(content: Text('Plato creado con éxito')),
          );
      },
      buildWhen: (previous, current) {
        return current.status != ManageRestaurantStatus.deleteFailure;
      },
      builder: (manageContext, state) {
        switch (state.status) {
          case ManageRestaurantStatus.failure:
            return const Center(child: Text('Fallo al cargar el restaurante'));
          case ManageRestaurantStatus.success:
            return _buildScreen(state, manageContext);
          case ManageRestaurantStatus.initial:
            return const Center(child: CircularProgressIndicator());
          case ManageRestaurantStatus.deleted:
            return const DeletedScreen();
          case ManageRestaurantStatus.editSuccess:
            return _buildScreen(state, manageContext);
          case ManageRestaurantStatus.deleteFailure:
            return Text("data");
          case ManageRestaurantStatus.createPlatoSuccess:
            return _buildScreen(state, manageContext);
        }
      },
    );
  }

  Scaffold _buildScreen(
      ManageRestaurantState state, BuildContext manageContext) {
    FilePickerResult? result;
    return Scaffold(
        appBar: AppBar(
          title: Text(state.restaurante!.nombre!),
          backgroundColor: Theme.of(context).colorScheme.onSecondary,
          foregroundColor: Theme.of(context).colorScheme.primary,
        ),
        body: SingleChildScrollView(
          child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              mainAxisSize: MainAxisSize.min,
              verticalDirection: VerticalDirection.down,
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Stack(alignment: Alignment.bottomCenter, children: <Widget>[
                  Image.network(
                    imgBase + state.restaurante!.coverImgUrl!,
                  ),
                  Positioned(
                      right: 15,
                      bottom: 15,
                      child: Tooltip(
                        child: CircleAvatar(
                          backgroundColor: Colors.red.shade700,
                          child: IconButton(
                            color: Colors.white,
                            onPressed: () async {
                              result = await FilePicker.platform.pickFiles(
                                withData: true,
                                allowMultiple: false,
                                allowedExtensions: ['jpg', 'png'],
                              );
                              if (result != null) {
                                BlocProvider.of<ManageRestaurantBloc>(context)
                                  ..add(ChangeImgEvent(state.restaurante!.id!,
                                      result!.files[0]));
                                setState(() {});
                                result?.files.forEach((element) {
                                  print(element.name);
                                });
                              }
                            },
                            icon: Icon(Icons.edit),
                          ),
                        ),
                        message: "Cambiar imagen",
                      )),
                ]),
                Padding(
                    padding: EdgeInsets.all(15),
                    child: Column(children: [
                      Row(
                        children: [
                          Expanded(
                            flex: 4,
                            child: Text("Nombre"),
                          ),
                          Expanded(
                              flex: 10,
                              child: Text(
                                state.restaurante!.nombre!,
                                style: TextStyle(
                                    fontWeight: FontWeight.w300, fontSize: 12),
                              ))
                        ],
                      ),
                      SizedBox(
                        height: 5,
                      ),
                      Row(
                        children: [
                          Expanded(
                            flex: 4,
                            child: Text("Descripción"),
                          ),
                          Expanded(
                              flex: 10,
                              child: Text(
                                state.restaurante!.descripcion!,
                                style: TextStyle(
                                    fontWeight: FontWeight.w300, fontSize: 12),
                              ))
                        ],
                      ),
                      SizedBox(
                        height: 5,
                      ),
                      Row(
                        children: [
                          Expanded(
                            flex: 4,
                            child: Text("Hora apertura"),
                          ),
                          Expanded(
                              flex: 10,
                              child: Text(
                                "${state.restaurante!.apertura!}",
                                style: TextStyle(
                                    fontWeight: FontWeight.w300, fontSize: 12),
                              ))
                        ],
                      ),
                      SizedBox(
                        height: 5,
                      ),
                      Row(
                        children: [
                          Expanded(
                            flex: 4,
                            child: Text("Hora cierre"),
                          ),
                          Expanded(
                              flex: 10,
                              child: Text(
                                "${state.restaurante!.cierre!}",
                                style: TextStyle(
                                    fontWeight: FontWeight.w300, fontSize: 12),
                              ))
                        ],
                      ),
                      SizedBox(
                        height: 5,
                      ),
                      Divider(),
                      SizedBox(
                        height: 5,
                      ),
                      ElevatedButton(
                          onPressed: () => Navigator.push(
                                  context,
                                  MaterialPageRoute(
                                      builder: (manageContext) =>
                                          RestaurantEditForm(
                                              restaurant: state.restaurante!)))
                              .then((successfulUpdate) => {
                                    if (successfulUpdate)
                                      ScaffoldMessenger.of(context)
                                          .showSnackBar(
                                        const SnackBar(
                                            content:
                                                Text('Modificado con éxito')),
                                      )
                                  }),
                          child: Text("Editar datos")),
                      SizedBox(
                        height: 5,
                      ),
                      ElevatedButton(
                          onPressed: () => Navigator.push(
                              context,
                              MaterialPageRoute(
                                  builder: (context) => ManagePlatosScreen(
                                        restaurantId: state.restaurante!.id!,
                                      ))),
                          child: Text("Gestionar platos")),
                      SizedBox(
                        height: 5,
                      ),
                      ElevatedButton(
                          onPressed: () => Navigator.push(
                              context,
                              MaterialPageRoute(
                                  builder: (manageContext) =>
                                      AddPlatoForm(context))),
                          child: Text("Añadir nuevo plato")),
                      SizedBox(
                        height: 5,
                      ),
                      ElevatedButton(
                          style: ElevatedButton.styleFrom(
                              backgroundColor: Colors.red.shade700,
                              foregroundColor: Colors.white),
                          onPressed: () =>
                              _dialogBuilder(context, state.restaurante!),
                          child: Text("Eliminar restaurante"))
                    ])),
              ]),
        ));
  }

  Future<void> _dialogBuilder(
      BuildContext dialogContext, RestauranteDetailResult restaurante) {
    return showDialog<void>(
      context: dialogContext,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('¿Eliminar ${restaurante.nombre}?'),
          content: Text(
              """¿Estás seguro de querer eliminar ${restaurante.nombre}? Esta acción no se puede deshacer. Por seguridad, debe eliminar los platos antes de poder eliminar el restaurante, le recomendamos desactivar los pedidos antes de optar por borrar definitivamente el restaurante de la aplicación."""),
          actions: <Widget>[
            ElevatedButton(
              style: TextButton.styleFrom(
                textStyle: Theme.of(context).textTheme.labelLarge,
              ),
              child: const Text('Cancelar'),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
            ElevatedButton(
              style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.red.shade700,
                  foregroundColor: Colors.white),
              child: const Text('Borrar'),
              onPressed: () {
                BlocProvider.of<ManageRestaurantBloc>(dialogContext)
                  ..add(DeleteRestaurantEvent(restaurante.id!));
                Navigator.of(context).pop();
              },
            ),
          ],
        );
      },
    );
  }

  void _showDeleteError() {
    ScaffoldMessenger.of(context).showSnackBar(SnackBar(
        content: Text(
            "No se ha podido borrar el restaurante, por favor trate de eliminar todos sus platos antes.")));
  }
}

class DeletedScreen extends StatefulWidget {
  const DeletedScreen({Key? key}) : super(key: key);

  @override
  State<DeletedScreen> createState() => _DeletedScreenState();
}

class _DeletedScreenState extends State<DeletedScreen> {
  @override
  void initState() {
    Timer(Duration(seconds: 3), () {
      Navigator.pushReplacement(
          context,
          MaterialPageRoute(
              builder: (context) => HomeScreen.withIndex(
                    indexInitial: 0,
                  )));
    });
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Theme.of(context).colorScheme.onPrimary,
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            const Icon(
              Icons.check_circle_outline_rounded,
              size: 100,
              color: Colors.white,
            ),
            const SizedBox(height: 10),
            const Text(
              'Borrado con éxito',
              style: TextStyle(fontSize: 24, color: Colors.white),
              textAlign: TextAlign.center,
            ),
            const SizedBox(height: 10),
          ],
        ),
      ),
    );
  }
}

class AddPlatoForm extends StatelessWidget {
  BuildContext formContext;

  AddPlatoForm(this.formContext);

  @override
  Widget build(BuildContext context) {
    return BlocProvider.value(
      value: BlocProvider.of<ManageRestaurantBloc>(formContext),
      child: AddPlatoFormUI(),
    );
  }
}

class AddPlatoFormUI extends StatefulWidget {
  AddPlatoFormUI();

  @override
  State<AddPlatoFormUI> createState() => _AddPlatoFormUIState();
}

class _AddPlatoFormUIState extends State<AddPlatoFormUI> {
  TextEditingController _nombreController = TextEditingController();
  TextEditingController _descripcionController = TextEditingController();
  TextEditingController _precioController = TextEditingController();
  TextEditingController _ingredientesController = TextEditingController();
  bool _sinGluten = false;
  FilePickerResult? result;
  bool imgSelected = false;

  @override
  Widget build(BuildContext context) {
    return BlocConsumer<ManageRestaurantBloc, ManageRestaurantState>(
      listenWhen: (previous, current) {
        return current.status == ManageRestaurantStatus.createPlatoSuccess ||
            current.status == ManageRestaurantStatus.failure;
      },
      listener: (context, state) {
        if (state.status == ManageRestaurantStatus.createPlatoSuccess) {
          Navigator.of(context).pop();
        } else if (state.status == ManageRestaurantStatus.failure) {
          ScaffoldMessenger.of(context)
              .showSnackBar(SnackBar(content: Text("Fallo al crear el plato")));
        }
      },
      buildWhen: (previous, current) {
        return current.status != ManageRestaurantStatus.failure;
      },
      builder: (context, state) {
        //if (state.status == ManageRestaurantStatus.editing) {
        return Scaffold(
            appBar: AppBar(
              leading: BackButton(
                onPressed: () {
                  Navigator.of(context).pop();
                },
              ),
              title: Text("Nuevo plato"),
              backgroundColor: Theme.of(context).colorScheme.onSecondary,
              foregroundColor: Theme.of(context).colorScheme.primary,
            ),
            body: SingleChildScrollView(
              child: Form(
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
                          child: Text("Precio"),
                        ),
                        Expanded(
                          flex: 10,
                          child: TextFormField(
                            keyboardType:
                                TextInputType.numberWithOptions(decimal: true),
                            controller: _precioController,
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
                            child: Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Text("Ingredientes"),
                                Text(
                                  "Separados por comas",
                                  style: TextStyle(
                                      fontWeight: FontWeight.w300,
                                      fontSize: 12),
                                )
                              ],
                            )),
                        Expanded(
                          flex: 10,
                          child: TextFormField(
                            minLines: 1,
                            maxLines: 6,
                            controller: _ingredientesController,
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
                          child: Text("Es sin gluten:"),
                        ),
                        Expanded(
                          flex: 10,
                          child: Checkbox(
                            value: _sinGluten,
                            onChanged: (bool? value) {
                              setState(() {
                                _sinGluten = !_sinGluten;
                                print(_sinGluten);
                              });
                            },
                          ),
                        )
                      ],
                    ),
                    ElevatedButton(
                        onPressed: () async {
                          result = await FilePicker.platform.pickFiles(
                            withData: true,
                            allowMultiple: false,
                            allowedExtensions: ['jpg', 'png'],
                          );
                          setState(() {
                            imgSelected = result != null;
                          });
                        },
                        child: Text("Seleccione una imagen")),
                    SizedBox(
                      height: 10,
                    ),
                    if (imgSelected)
                      Stack(
                          alignment: Alignment.bottomCenter,
                          children: <Widget>[
                            ClipRRect(
                              borderRadius: BorderRadius.circular(8.0),
                              child: Image.file(
                                File(result!.files[0].path!),
                                fit: BoxFit.cover,
                                width: double.infinity,
                              ),
                            ),
                            Positioned(
                              right: 8,
                              top: 8,
                              child: IconButton(
                                color: Colors.white,
                                onPressed: () {
                                  setState(() {
                                    result = null;
                                    imgSelected = false;
                                  });
                                },
                                icon: Icon(Icons.close),
                              ),
                            ),
                          ]),
                    if (imgSelected)
                      SizedBox(
                        height: 10,
                      ),
                    if (imgSelected)
                      ElevatedButton(
                        onPressed: () {
                          context.read<ManageRestaurantBloc>().add(AddPlato(
                              PlatoRequest(
                                  _nombreController.text,
                                  _descripcionController.text,
                                  double.parse(_precioController.text),
                                  _ingredientesController.text.split(","),
                                  _sinGluten),
                              result!.files[0]));
                        },
                        child: const Text('Submit'),
                      ),
                  ],
                ),
              )),
            ));
        // } else {
        //   return Scaffold(
        //     body: Center(
        //       child: CircularProgressIndicator(),
        //     ),
        //   );
        // }
      },
    );
  }
}
