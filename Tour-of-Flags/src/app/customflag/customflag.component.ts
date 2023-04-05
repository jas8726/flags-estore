import { Component } from '@angular/core';

import { CustomFlagService} from '../customflag.service';


@Component({
  selector: 'app-customflag',
  templateUrl: './customflag.component.html',
  styleUrls: ['./customflag.component.css']
})

export class CustomflagComponent {
  
  constructor(
    private customFlagService: CustomFlagService ){}
  
  getRows(): void {
    this.customFlagService.getHStripe();
  }

  setRows(): void{
    //var x = (document.getElementById("a") as HTMLElement).value;
    var x = document.getElementById("a")?.nodeValue;
    if(x == null){ 
      //(x as unknown) as number;
      let x = 0;
    }
    this.customFlagService.setHStripe((x as unknown) as number);
    //alert(x);
  }
}
