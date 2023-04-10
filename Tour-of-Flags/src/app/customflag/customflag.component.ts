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
  public rcolors = ['#ff0000', '#ff0000', '#ff0000', '#ff0000', '#ff0000', '#ff0000'];
  private ccolors = ['#ff0000', '#ff0000', '#ff0000', '#ff0000', '#ff0000', '#ff0000'];
  private coltoggle = [false, false, false, false, false, false];
  public tmpclassname = "";

    getRowHeight(): number{
      return 100/this.rows;
    }

    getCWidth(): number{
      return 200/this.cols;
    }


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
  }
  setCColor(i: any, hx: any): void{
    var index = (i as number) -1;
    this.ccolors[index] = hx as string;
  }


  getRColor(i: any): string{
    return this.rcolors[i-1];
  }
  getCColor(i: any): string{
    return this.ccolors[i-1];
  }

  validRIndex(i: number): boolean {
    if(i-1 < this.rows){
      return true;
    }
    return false
  }

  validCIndex(i: number): boolean {
    if(i-1 < this.cols){
      return true;
    }
    return false
  }
  isColToggle(i: number): boolean{
    return this.coltoggle[i-1];
  }
  setToggle(i: number, x: any): void{
    this.coltoggle[i-1] = x as boolean;
  }
}