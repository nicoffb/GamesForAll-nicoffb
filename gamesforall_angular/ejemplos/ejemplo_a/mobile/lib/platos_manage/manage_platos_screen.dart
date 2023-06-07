import 'package:file_picker/file_picker.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:front/platos_manage/platos_manage_bloc.dart';
import 'package:front/platos_manage/platos_manage_event.dart';

import '../landing/landing_screen.dart';
import '../model/plato_list_result.dart';
import '../model/plato_request.dart';
import '../restaurantmenu/restaurant_screen.dart';

String id = "";
const String imgBasePlato = "http://localhost:8080/download/";

class ManagePlatosScreen extends StatelessWidget {
  ManagePlatosScreen({super.key, required this.restaurantId});

  String restaurantId;

  @override
  Widget build(BuildContext context) {
    id = restaurantId;
    return BlocProvider(
      create: (context) =>
          PlatosManageBloc()..add(PlatosFetchedEvent(restaurantId)),
      child: ManagePlatosScreenUI(),
    );
  }
}

class ManagePlatosScreenUI extends StatefulWidget {
  @override
  State<ManagePlatosScreenUI> createState() => _ManagePlatosScreenUIState();
}

class _ManagePlatosScreenUIState extends State<ManagePlatosScreenUI> {
  final _scrollController = ScrollController();

  @override
  void initState() {
    super.initState();
    _scrollController.addListener(_onScroll);
  }

  @override
  Widget build(BuildContext context) {
    return BlocConsumer<PlatosManageBloc, PlatosManageState>(
      listenWhen: (previous, current) {
        return current.status == PlatosManageStatus.editSuccess;
      },
      listener: (context, state) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Modificado con éxito')),
        );
        context
            .read<PlatosManageBloc>()
            .add(PlatosFetchedEvent(state.restaurantId));
      },
      builder: (context, state) {
        switch (state.status) {
          case PlatosManageStatus.failure:
            return Scaffold(
                appBar: AppBar(
                  title: Text("Platos"),
                  backgroundColor: Theme.of(context).colorScheme.onSecondary,
                  foregroundColor: Theme.of(context).colorScheme.primary,
                ),
                body: Center(
                  child: Text("Parece que no hemos encontrado ningún plato."),
                ));
          case PlatosManageStatus.success:
            return Scaffold(
              appBar: AppBar(
                title: Text("Platos"),
                backgroundColor: Theme.of(context).colorScheme.onSecondary,
                foregroundColor: Theme.of(context).colorScheme.primary,
              ),
              body: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  mainAxisSize: MainAxisSize.min,
                  verticalDirection: VerticalDirection.down,
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Expanded(
                        child: ListView.builder(
                      shrinkWrap: true,
                      itemBuilder: (BuildContext context, int index) {
                        return index >= state.platos.length
                            ? const BottomLoader()
                            : PlatoManageItem(plato: state.platos[index]);
                      },
                      itemCount: state.hasReachedMax
                          ? state.platos.length
                          : state.platos.length + 1,
                      controller: _scrollController,
                    ))
                  ]),
            );
          case PlatosManageStatus.initial:
            return const Center(child: CircularProgressIndicator());
          case PlatosManageStatus.editing:
            return const Center(child: CircularProgressIndicator());
          case PlatosManageStatus.editSuccess:
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
    if (_isBottom) context.read<PlatosManageBloc>().add(PlatosFetchedEvent(id));
  }

  bool get _isBottom {
    if (!_scrollController.hasClients) return false;
    final maxScroll = _scrollController.position.maxScrollExtent;
    final currentScroll = _scrollController.offset;
    return currentScroll >= (maxScroll * 0.9);
  }
}

class PlatoManageItem extends StatefulWidget {
  const PlatoManageItem({super.key, required this.plato});

  final PlatoGeneric plato;

  @override
  State<PlatoManageItem> createState() => _PlatoManageItemState();
}

class _PlatoManageItemState extends State<PlatoManageItem> {
  FilePickerResult? result;
  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: EdgeInsets.only(left: 5, right: 5),
      child: Container(
        height: 70,
        child: ListTile(
          leading: Image.network(
            imgBasePlato + widget.plato.imgUrl!,
            fit: BoxFit.contain,
          ),
          title: Text(widget.plato.nombre!),
          subtitle: Text("${widget.plato.precio!} €"),
          trailing: Row(
            mainAxisSize: MainAxisSize.min,
            children: [
              Padding(
                padding: EdgeInsets.all(2),
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
                          BlocProvider.of<PlatosManageBloc>(context)
                            ..add(ChangeImgEvent(
                                widget.plato.id!, result!.files[0]));
                          setState(() {});
                          result?.files.forEach((element) {
                            print(element.name);
                          });
                        }
                      },
                      icon: Icon(Icons.image),
                    ),
                  ),
                  message: "Cambiar imagen",
                ),
              ),
              Padding(
                padding: EdgeInsets.all(2),
                child: Tooltip(
                  child: CircleAvatar(
                    backgroundColor: Colors.red.shade700,
                    child: IconButton(
                      color: Colors.white,
                      onPressed: () => Navigator.push(
                          context,
                          MaterialPageRoute(
                              builder: (manageContext) =>
                                  PlatoEditForm(context, widget.plato.id!))),
                      icon: Icon(Icons.edit),
                    ),
                  ),
                  message: "Editar plato",
                ),
              ),
              Padding(
                padding: EdgeInsets.all(2),
                child: Tooltip(
                  child: CircleAvatar(
                    backgroundColor: Colors.red.shade700,
                    child: IconButton(
                      color: Colors.white,
                      onPressed: () => _dialogBuilder(context, widget.plato),
                      icon: Icon(Icons.delete),
                    ),
                  ),
                  message: "Borrar plato",
                ),
              ),
            ],
          ),
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(10.0),
          ),
        ),
      ),
    );
  }

  Future<void> _dialogBuilder(BuildContext dialogContext, PlatoGeneric plato) {
    return showDialog<void>(
      context: dialogContext,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('¿Eliminar ${plato.nombre}?'),
          content: Text(
              """¿Estás seguro de querer eliminar ${plato.nombre}? Esta acción no se puede deshacer. Se eliminarán además todas las valoraciones que tenga el plato."""),
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
                BlocProvider.of<PlatosManageBloc>(dialogContext)
                  ..add(DeletePlatoEvent(plato.id!));
                Navigator.of(context).pop();
              },
            ),
          ],
        );
      },
    );
  }
}

class PlatoEditForm extends StatelessWidget {
  BuildContext formContext;
  String platoId;

  PlatoEditForm(this.formContext, this.platoId);

  @override
  Widget build(BuildContext context) {
    return BlocProvider.value(
      value: BlocProvider.of<PlatosManageBloc>(formContext)
        ..add(PlatoDetailFetchedEvent(platoId)),
      child: PlatoEditFormUI(),
    );
  }
}

class PlatoEditFormUI extends StatefulWidget {
  PlatoEditFormUI();

  @override
  State<PlatoEditFormUI> createState() => _PlatoEditFormUIState();
}

class _PlatoEditFormUIState extends State<PlatoEditFormUI> {
  TextEditingController _nombreController = TextEditingController();
  TextEditingController _descripcionController = TextEditingController();
  TextEditingController _precioController = TextEditingController();
  TextEditingController _ingredientesController = TextEditingController();
  late bool _sinGluten;

  @override
  Widget build(BuildContext context) {
    return BlocConsumer<PlatosManageBloc, PlatosManageState>(
      listenWhen: (previous, current) {
        return current.status == PlatosManageStatus.editing ||
            current.status == PlatosManageStatus.editSuccess ||
            current.status == PlatosManageStatus.failure;
      },
      listener: (context, state) {
        if (state.status == PlatosManageStatus.editing) {
          _nombreController.text = state.platoEditing!.nombre!;
          _descripcionController.text = state.platoEditing!.nombre!;
          _precioController.text = "${state.platoEditing!.precio!}";
          _ingredientesController.text =
              state.platoEditing!.ingredientes!.join(",");
          _sinGluten = state.platoEditing!.sinGluten!;
        } else if (state.status == PlatosManageStatus.editSuccess) {
          Navigator.of(context).pop();
        } else if (state.status == PlatosManageStatus.failure) {
          ScaffoldMessenger.of(context).showSnackBar(
              SnackBar(content: Text("Fallo al editar el plato")));
        }
      },
      buildWhen: (previous, current) {
        return current.status != PlatosManageStatus.failure;
      },
      builder: (context, state) {
        if (state.status == PlatosManageStatus.editing) {
          return Scaffold(
              appBar: AppBar(
                leading: BackButton(
                  onPressed: () {
                    context.read<PlatosManageBloc>().add(EditCancelled());
                    Navigator.of(context).pop();
                  },
                ),
                title: Text("Editar ${state.platoEditing!.nombre}"),
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
                      onPressed: () {
                        context.read<PlatosManageBloc>().add(EditPlato(
                            state.platoEditing!.id!,
                            PlatoRequest(
                                _nombreController.text,
                                _descripcionController.text,
                                double.parse(_precioController.text),
                                _ingredientesController.text.split(","),
                                _sinGluten)));
                      },
                      child: const Text('Submit'),
                    ),
                  ],
                ),
              )));
        } else if (state.status == PlatosManageStatus.failure) {
          return Text("Fallo");
        } else {
          return Scaffold(
            body: Center(
              child: CircularProgressIndicator(),
            ),
          );
        }
      },
    );
  }
}
