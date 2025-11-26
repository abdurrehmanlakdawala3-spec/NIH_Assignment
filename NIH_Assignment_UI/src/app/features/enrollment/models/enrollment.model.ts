export interface EnrollmentView {
  id?: number;
  studentId: number;
  courseId: number;
  studentFullName?: string;
  courseCode?: string;
  courseTitle?: string;
}

export interface EnrollmentCreateRequest {
  studentId: number;
  courseId: number;
}