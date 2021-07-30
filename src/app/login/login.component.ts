import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { User } from '../Models/login';
import { LoginService } from '../Services/login.service';
import { Logindata } from '../Logindata';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private toastr: ToastrService, private service: LoginService, private router: Router) {
  }
  // Lifecycle methods
  ngOnInit(): void {

  }
  model: User = new User();
  register() {

  }
  hide = true;

  LoginUser(value: any, f: NgForm) {

    console.log(value);


    this.service.loginApi(value).subscribe(
      res => {
        this.validateLogin(res, f)

      }
      ,
      error => {
        console.log("Exception Occured")
      }
    )





  }

  validateLogin(res: any, f: NgForm) {
    let data = res
    console.log(data)
    if (res == "success") {
      this.toastr.success('Login Successfull', '', { positionClass: 'toast-top-right' });
      this.router.navigateByUrl("/edit/" + this.model.id)

      f.resetForm();
    }
    else {
      this.toastr.error('Login Unsuccessfull');
    }

  }



  gotoForgotPSWD() {
    this.router.navigate(['forgotpassword'])
  }
  gotoSignup() {
    this.router.navigate(['signup'])
  }
}
