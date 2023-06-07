import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'userImage'
})
export class UserImagePipe implements PipeTransform {

  transform(value: unknown, ...args: unknown[]): unknown {
    return null;
  }

}
