import { Component, Input } from '@angular/core';
import { Step } from 'src/app/models/step.interface';

@Component({
  selector: 'app-step-card',
  templateUrl: './step-card.component.html',
  styleUrls: ['./step-card.component.css'],
})
export class StepCardComponent {
  @Input() step: Step = {} as Step;
}
