import { Directive, ElementRef } from '@angular/core';
import { filter, map, of, take } from 'rxjs';

@Directive({
  selector: '[appAutofocus]'
})
export class AutofocusDirective {

  constructor(private el: ElementRef) { }

  ngOnInit() {
      of(this.el)
      .pipe(
        map(elementRef => elementRef.nativeElement), 
        filter(nativeElement => !!nativeElement), 
        take(1) 
      )
      .subscribe(input => {
        input.focus();
      })
  }
}
