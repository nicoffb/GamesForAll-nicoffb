import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'artworkImage'
})
export class ArtworkImagePipe implements PipeTransform {

  transform(image: string): string {
    const urlBase = 'http://localhost:8080'
    if(!image) {
      return 'https://blog.sinapsis.agency/wp-content/uploads/2021/04/DEFINICIONES.-ERROR-404.png';
    } else if (image) {
      return `${urlBase}/download/${image}`;
    } else {
      return 'https://blog.sinapsis.agency/wp-content/uploads/2021/04/DEFINICIONES.-ERROR-404.png';
    }
  }

}
