import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, NgForm, Validators } from '@angular/forms';
import { MatRadioButton, MatRadioChange, MatRadioModule } from '@angular/material/radio';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { EditService } from '../Services/edit.service';
interface Role {
  value: string;
  viewValue: string;
}
@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit {
  jobrole: Role[] = [
    { value: 'Associate Consultant', viewValue: 'Associate Consultant' },
    { value: 'Consultant', viewValue: 'Consultant' },
    { value: 'Senior Consultant', viewValue: 'Senior Consultant' }
  ];
  firstFormGroup: FormGroup
  data: any
  employee =
    {

      id: {
        employeeId: "string"
      },
      name: "string",
      email: "string",
      doj: "2021-06-25T06:55:27.532Z",
      password: "string",
      projectId: "string",
      projectname: "",
      jobrole: "",
      gender: "Male"
    }
  name = "";
  maxDate = new Date();
  id:any;

  constructor(private _formBuilder: FormBuilder, private router: Router, 
    private route:ActivatedRoute,private service: EditService, private toastr: ToastrService) {
    this.id=this.route.snapshot.params.id;
    this.firstFormGroup = this._formBuilder.group({
      EmployeeID: ['', Validators.required],
      DOJ: ['', Validators.required],
      Name: ['', Validators.required],
      Password: ['', Validators.required],
      jobctrl: ['', Validators.required]
    });
  }
  ngOnInit(): void {
    this.getService(this.id);
  }

  get form2() {
    return this.firstFormGroup.controls;
  }

  onSubmit(form: NgForm) {
    console.log(form);
    this.updateService();
    this.name = this.data.name;

  }



  email = new FormControl('', [Validators.required, Validators.email]);
  jobctrl = new FormControl('', [Validators.required, Validators.email]);
  password = new FormControl();

  getErrorMessage() {
    if (this.email.hasError('required')) {
      return 'You must enter a value';
    }

    return this.email.hasError('email') ? 'Not a valid email' : '';
  }


  getLogoutMessage() {
    localStorage.removeItem('token')
  }

  getSave() {
    alert("Details Updated Successfully" + this.data.name);
    this.updateService();
    this.name = this.data.name;
  }

  getService(id: string) {

    this.service.getService(id).subscribe(res => {
      this.data = res
      this.name = this.data.name
      let converteddate = new Date(this.data.doj);
      this.data.doj = new Date(converteddate.toLocaleDateString());


    }, error => console.log("error"))
  }

  onChange(mrChange: MatRadioChange) {
    this.data.gender = mrChange.value

    let mrButton: MatRadioButton = mrChange.source;

  }

  updateService() {
    this.employee.id.employeeId = this.data.id.employeeId;
    this.employee.name = this.data.name;
    this.employee.password = this.data.password;
    this.employee.jobrole = this.data.jobrole;
    this.employee.gender = this.data.gender;
    this.employee.email = this.data.email;
    this.employee.doj = this.data.doj;
    this.employee.projectId = this.data.projectId
    this.employee.projectname = this.data.projectname
    this.service.updateService(this.employee).subscribe(res => {
      this.callback(res)
    },
      err => {
        this.toastr.error('Updating Failed', '', { positionClass: 'toast-top-right' });
      });



  }
  callback(res: any) {
    console.log(res)
    if (res == "Success") {
      this.toastr.success('Details Updated Successfully', '', { positionClass: 'toast-top-right' });
    } else {
      this.toastr.error('Updating Failed', '', { positionClass: 'toast-top-right' });
    }


  }
  time: any;
  date: any;
  calculateDate() {
    let milliSeconds = new Date().getTime();
    let date = new Date(milliSeconds);
    this.time = date.toLocaleTimeString();
    this.date = date.toLocaleDateString();
    console.log('time', this.time, this.date);
  }






}
