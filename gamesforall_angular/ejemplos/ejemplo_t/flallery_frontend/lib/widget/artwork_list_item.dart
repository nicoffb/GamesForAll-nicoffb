import 'package:flallery_frontend/models/artwork_list_response.dart';
import 'package:flallery_frontend/pages/artwork_details_page.dart';
import 'package:flutter/material.dart';
/*
class ArtworkListItem extends StatelessWidget {
  const ArtworkListItem({super.key, required this.artwork});

  final Artwork artwork;

  @override
  Widget build(BuildContext context) {
    final textTheme = Theme.of(context).textTheme;
    return Material(
      child: ListTile(
        leading: Text('${artwork.name}', style: textTheme.bodySmall),
        title: Text(artwork.description!),
        isThreeLine: true,
        subtitle: Text(artwork.owner!),
        dense: true,
      ),
    );
  }
}*/

class ArtworkListItem extends StatelessWidget {
  const ArtworkListItem({Key? key, required this.artwork}) : super(key: key);

  final Artwork artwork;

  @override
  Widget build(BuildContext context) {
    final textTheme = Theme.of(context).textTheme;
    return Material(
      child: InkWell(
        onTap: () {
          Navigator.push(
            context,
            MaterialPageRoute(
              builder: (context) => ArtworkDetailsPage(artwork: artwork),
            ),
          );
        },
        child: ListTile(
          leading:
              Image.network("http://localhost:8080/download/${artwork.imgUrl}"),
          title: Text(
            artwork.name!,
            style: textTheme.bodyLarge,
          ),
          isThreeLine: true,
          subtitle: Text(artwork.owner!),
          dense: true,
          trailing: Icon(Icons.arrow_forward),
        ),
      ),
    );
  }
}
