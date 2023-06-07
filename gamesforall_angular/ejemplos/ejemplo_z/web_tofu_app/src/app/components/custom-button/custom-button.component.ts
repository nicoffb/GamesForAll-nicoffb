import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-custom-button',
  templateUrl: './custom-button.component.html',
  styleUrls: ['./custom-button.component.css']
})
export class CustomButtonComponent {
  @Input() label = "";
  @Input() btnStyle = "";
  @Input() isDisabled = false;
  @Input() fontSize = 30;
  @Input() verticalPadding = 18;

  @Input() btnTextAlign = "center";
  @Input() isActive = false;


  @Output() OnClick = new EventEmitter()

  emitEvent(){
    this.OnClick.emit()
  }
}
