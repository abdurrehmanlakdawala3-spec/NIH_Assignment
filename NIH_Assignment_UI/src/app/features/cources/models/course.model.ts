export interface Course {
  id?: number;
  courseCode: string;
  title: string;
  description?: string;
}

export interface CourseRequest {
  courseCode?: string;
  title?: string;
  description?: string;
}

