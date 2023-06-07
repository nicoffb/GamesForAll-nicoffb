import 'package:flallery_frontend/blocs/artwork_list/artwork_list_bloc.dart';
import 'package:flallery_frontend/view/artwork_list.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:http/http.dart' as http;
/*
class ArtworksPage extends StatelessWidget {
  const ArtworksPage({super.key});
  
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Artworks')),
      body: BlocProvider(
        create: (_) => ArtworkBloc(httpClient: http.Client())..add(ArtworkFetched()),
        child: const ArtworksList(),
      ),
    );
  }
}*/