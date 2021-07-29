import { Component } from '@angular/core';
import { User } from './Models/login';
import { ToastrService } from 'ngx-toastr'
import { NgForm } from '@angular/forms';
import { LoginService } from './Services/login.service';
import { Router } from '@angular/router';
import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';
import { Logindata } from './Logindata';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'MiniProject';
  loginhide = true;
  hidevalue = false;
  
   
  
  constructor(private router: Router) {
    
 }
  
  ngOnInIt() {
    
  }

}

