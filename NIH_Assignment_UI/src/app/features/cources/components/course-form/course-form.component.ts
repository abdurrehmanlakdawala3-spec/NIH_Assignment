import { CommonModule } from "@angular/common";
import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { MatButtonModule } from "@angular/material/button";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { CourseService } from "../../services/course.service";
import { ActivatedRoute, Router } from "@angular/router";

@Component({
  standalone: true,
  templateUrl: './course-form.component.html',
imports: [CommonModule, ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButtonModule],
})
export class CourseFormComponent implements OnInit {

  form!: FormGroup;
  courseId?: number;

  constructor(
    private fb: FormBuilder,
    private service: CourseService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(){
    this.form = this.fb.group({
      title: ['', Validators.required],
      courseCode: ['', Validators.required],
      description: ['']
    });

    this.courseId = +this.route.snapshot.paramMap.get('id')!;

    if(this.courseId){
      this.service.getById(this.courseId).subscribe(res => {
        this.form.patchValue(res);
      });
    }
  }

  submit(){
    if(this.form.invalid){
      return;
    }

    if(this.courseId){
      this.service.update(this.courseId, this.form.value).subscribe(() => {
        this.router.navigateByUrl('/courses');
      });
    } else {
      this.service.create(this.form.value).subscribe(() => {
        this.router.navigateByUrl('/courses');
      });
    }
  }
}
