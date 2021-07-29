import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MustMatch } from '../MustMatch';
import { ForgotpswdService } from '../services/forgotpswd.service';
import { MatStepper } from '@angular/material/stepper';
import { UpdatepswdService } from '../services/updatepswd.service';
import { MatDialog,MatDialogConfig, MatDialogRef } from '@angular/material/dialog';
import { ReusabledialogComponent } from '../reusabledialog/reusabledialog.component';
//import {AlertDialogComponent} from './alert-dailog/alert-dailog.component';

@Component({
  selector: 'app-forgotpswd',
  templateUrl: './forgotpswd.component.html',
  styleUrls: ['./forgotpswd.component.css']
})
export class ForgotpswdComponent implements OnInit {

  firstFormGroup: FormGroup
  secondFormGroup: FormGroup
  showspinner= false
  stepper: any
  updateflag: any
  pswddata = {

    employeeId: "",
    password: ""

  }
  pswdresetstatus=""
  key: any
  data: any
  employee: any
  retryscount = 2;
  errormessage = ""
  redirectflag = false
  constructor(private _formBuilder: FormBuilder, private router: Router, private dailog:MatDialog,
    private forgotpswdservice: ForgotpswdService, private updatepswdservice: UpdatepswdService) {
    this.firstFormGroup = this._formBuilder.group({
      firstCtrl: ['', Validators.required]
    });
    this.secondFormGroup = this._formBuilder.group({
      secondCtrl: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(5)]],
      thirdCtrl: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(8)]],
      fourthCtrl: ['', Validators.required],
    }, {
      validator: MustMatch('thirdCtrl', 'fourthCtrl')
    });
  }
  ngOnInit() {
    this.firstFormGroup.reset()
    this.secondFormGroup.reset()
    this.firstFormGroup.markAllAsTouched()
    this.secondFormGroup.markAllAsTouched()
  }
  get form1() {
    return this.firstFormGroup.controls;
  }
  get form2() {
    return this.secondFormGroup.controls;
  }
  gotoLogin() {
    this.router.navigate(['login'])
  }
  sendmail(stepper: MatStepper) {
    console.log(this.form1.firstCtrl.value);
    this.stepper = stepper;
    this.CallSpinnerDailog();
    this.mailservice();
    
  }
  mailservice() {
    this.forgotpswdservice.sendmail(this.form1.firstCtrl.value)
      .subscribe(
        res => {
        console.log(res);
        this.timeoutfunction(res)
      },
        error =>  {
          this.errormessage = error;
          console.log("error check");
          console.log(this.errormessage)
         // this.closeDialog();
            //this.gotoLogin();
          setTimeout(() => {
            this.dailog.closeAll()
            this.gotoLogin()
          }, 50);

        }
      );

  }

  timeoutfunction(res: any) {
    this.key = res
    console.log(this.key.key)
    if (this.key.key > 0) {
      this.closeDialog()
      this.stepper.next()
    }
    else {
      //this.router.navigate(['signup'])
      this.closeDialog()
      this.callDialog()
      //this.gotoLogin()
      
    }
    // }, 5);
  }

  redirectToSignUp() {
    //alert("Invalid EmployeeId. Redirecting To SignUp")
    this.gotoLogin()
  }

  validate() {
    console.log(this.form2.secondCtrl.value)
    if ((this.key.key != this.form2.secondCtrl.value) && this.retryscount > 0) {
     // alert("Invalid Key. Redirecting to the Previous Step . Retrys Left : " + this.retryscount)
     let message="Invalid Key. Retrys Left:"+this.retryscount;
       this.retryDialog(message)
     // this.stepper.previous()
     // this.firstFormGroup.reset()
     // this.secondFormGroup.reset()
     // this.retryscount = this.retryscount - 1

    }
    else if ((this.key.key != this.form2.secondCtrl.value) && this.retryscount == 0) {
     // alert("Invalid Key. Redirecting to the Login Page . Retrys Left:" + this.retryscount)
      //this.gotoLogin()
      this.navigationDialog("Redirecting to login page")
    }
    else {
      this.pswddata.employeeId = this.key.employeeId
      this.pswddata.password = this.form2.fourthCtrl.value
      this.updatepswdservice.updatepswd(this.pswddata).subscribe(res =>
      {
        let status=res;
        if(status=="success")
        {
          this.pswdresetstatus="Password Reseted Successfuly"
          console.log("Success method")
          console.log(res);
          this.stepper.next();

        }
        else{
          this.pswdresetstatus="Something went wrong"
          console.log("Failed method")
          this.stepper.next();
        }
      } ,
        error => {
          this.pswdresetstatus="Something went wrong"
          console.log("error,error",error);
        this.stepper.next();
          this.errormessage = error
        }
      );
      // this.stepper.next()
    }

  }
  
  callDialog(){
   let dialogref= this.dailog.open(ReusabledialogComponent,{data:{title:'Error',content:'Invalid EmployeeId',showspinner:false}});
   let result:any
   dialogref.afterClosed().subscribe(
     (res: any)=>{
       result=res
       console.log("Dialog Result:",res)
       if(res=="true")
       {
         this.closeDialog()
       //  console.log("1")
         this.gotoLogin()
       }
       //console.log("2")
     }
   )
  
  }

  CallSpinnerDailog(){
    let dialogref= this.dailog.open(ReusabledialogComponent,{data:{showspinner:true}});

   dialogref.afterClosed().subscribe(
     (res: any)=>{
       console.log("Dialog Result:",res)
     }
     
   )
  }
  closeDialog(){
    if(this.key.key>=0){
      this.dailog.closeAll()
    }
  }
  
  
  retryDialog(message:String){
    let dialogref= this.dailog.open(ReusabledialogComponent,{data:{title:'Warning',content:message,showspinner:false}});
    let result:any
    dialogref.afterClosed().subscribe(
      (res: any)=>{
        result=res
        console.log("Dialog Result:",res)
        if(res=="true")
        {
          this.closeDialog()
          this.stepper.previous()
          this.firstFormGroup.reset()
          this.secondFormGroup.reset()
          this.retryscount = this.retryscount - 1
        }
        
        console.log("2")
      }
    )
   
   }
 
   navigationDialog(message:String){
    let dialogref= this.dailog.open(ReusabledialogComponent,{data:{title:'Warning',content:message,showspinner:false}});
    let result:any
    dialogref.afterClosed().subscribe(
      (res: any)=>{
        result=res
        console.log("Dialog Result:",res)
        if(res=="true")
        {
          this.closeDialog()
          this.gotoLogin()
        }
        
        console.log("2")
      }
    )
   
   }
 
}
