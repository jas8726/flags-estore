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
  public rcolors = ['none', 'none', 'none', 'none', 'none', 'none'];
  private ccolors = ['none', 'none', 'none', 'none', 'none', 'none'];
  public r1color: string = '#ff0000';
  public styleOne = "#fea1c08";

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

  setRColor(i: any, hx: any): void{
    var index = (i as number) -1;
    this.rcolors[index] = hx as string;
    if(index == 0){
      this.r1color = hx as string;
      console.log("setcolor called- r1color=" + this.r1color);
    }
  }


}