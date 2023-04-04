import { Component } from '@angular/core';

import { CustomFlag } from '../customflag';
type x= BigInt;

@Component({
  selector: 'app-customflag',
  templateUrl: './customflag.component.html',
  styleUrls: ['./customflag.component.css']
})

export class CustomflagComponent {
  
  constructor(){}
  
  setRows(BigInt: x): void {
    this.CustomFlag.setHorStripes(x);
  }
}
