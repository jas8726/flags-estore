import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

//import { CustomFlag } from './customflag';
import { MessageService } from './message.service';



@Injectable({ providedIn: 'root' })
export class CustomFlagService{

    private flagsUrl = 'http://localhost:8080/customflag';  // URL to web api
    
    private rows = 0;
    private cols = 0;
    private rcolors = [-1, -1, -1, -1, -1, -1];
    private ccolors = [-1, -1, -1, -1, -1, -1];

    httpOptions = {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' })
      };
    
      constructor(
        private http: HttpClient,
        private messageService: MessageService,
        //public custom: CustomFlag 
        
        ) { }
    
    getHStripe(): number {
        return this.rows;
    }
    
    getVStripe(): number{
        return this.cols;
    }


    setHStripe(x: number): void{
        this.rows = x;
    }

    setVStripe(x: number): void{
        this.cols = x;
    }


    getHColor(i: number): number{
        return this.rcolors[i];
    }

    getVColor(i: number): number{
        return this.ccolors[i];
    }


    setHColor(i: number, x: number): void{
        this.rcolors[i] = x;
    }

    setVColor(i: number, x: number): void{
        this.ccolors[i] = x;
    }
}