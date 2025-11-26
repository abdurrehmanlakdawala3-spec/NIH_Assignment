export interface Student {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  dateOfBirth: string;
}

export interface StudentRequest {
  firstName?: string;
  lastName?: string;
  email?: string;
  dateOfBirth?: string;
}
