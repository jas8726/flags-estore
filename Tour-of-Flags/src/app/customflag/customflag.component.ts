import { Component } from '@angular/core';
import { AccountService } from '../account.service';
import { Flag } from '../flag';
import { FlagService } from '../flag.service';
import { CustomFlagService} from '../customflag.service';


@Component({
  selector: 'app-customflag',
  templateUrl: './customflag.component.html',
  styleUrls: ['./customflag.component.css']
})

export class CustomflagComponent {
  customFlag: Flag = {} as Flag;
  errorText: String = "";

  readonly CUSTOM_IMAGE: string = "https://cdn.discordapp.com/attachments/1070502517760335994/1097918011387687112/Sprint_4_Presentation.png";

  constructor(
    public customFlagService: CustomFlagService,
    private flagService: FlagService,
    public accountService: AccountService ){}
  

  public rows: number = 1;
  public cols: number = 1;
  public rcolors = ['#ff0000', '#ff0000', '#ff0000', '#ff0000', '#ff0000', '#ff0000'];
  private ccolors = ['#ff0000', '#ff0000', '#ff0000', '#ff0000', '#ff0000', '#ff0000'];
  private coltoggle = [false, false, false, false, false, false];
  public tmpclassname = "";

  ableToAddToCart(): boolean {
    return (this.accountService.getCurrentAccount() != null) && !this.accountService.isAdmin();
  }

  addcustomtocart():void{
    if (this.customFlag.name.trim() === "") {
      this.errorText = "Please enter a name for your custom flag.";
    }
    else {
      this.add();
    }
  }

  add(): void {
    this.customFlag.name = this.customFlag.name + " (Custom)"
    this.customFlag.price = 20;
    this.customFlag.image = this.CUSTOM_IMAGE;
    this.customFlag.quantity = 1;
    this.customFlag.tags = [];
    this.flagService.addFlag(this.customFlag)
      .subscribe(flag => {
        this.addToCart(flag);
      });
  }

  addToCart(flag: Flag): void {
    this.accountService.addFlagCart(this.accountService.getCurrentAccount()!.username, flag.id).subscribe(success => {
      if (success) {
        this.errorText = "Successfully added " + flag.name + " to cart.";
      }
      else {
        this.errorText = "Error adding flag to cart.";
      }
    });
  }
      



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
    if(i ==  0){
    console.log('iscoltoggle(1)= ' + this.coltoggle[i-1]);
    }
    return this.coltoggle[i-1];
  }
  setToggle(i: number, x: any): void{
    this.coltoggle[i-1] = x as boolean;
    console.log('toggle: ' + this.coltoggle)
  }
}