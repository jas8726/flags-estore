import { Component } from '@angular/core';

import { CustomFlagService} from '../customflag.service';


@Component({
  selector: 'app-customflag',
  templateUrl: './customflag.component.html',
  styleUrls: ['./customflag.component.css']
})

export class CustomflagComponent {
  
  constructor(
    public customFlagService: CustomFlagService ){}
  

  public rows: number = 1;
  public cols: number = 1;
  private rcolors = [-1, -1, -1, -1, -1, -1];
  private ccolors = [-1, -1, -1, -1, -1, -1];

  getRows(): void {
    this.rows;
  }
  setRows(x: any): void{
    if(x == null){ 
      let x = 0;
    }
    this.rows = x as number;
  }

  
  getCols(): void {
    this.cols;
  }
  setCols(x: any): void{
    if(x == null){ 
      let x = 0;
    }
    this.cols = x as number;
  }

}