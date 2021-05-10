import { Achievement } from "./Achievement";

export enum Role {
  User = "USER",
  Admin = "ADMIN",
}

export interface User {
  id?: number;
  username: string;
  password: string;
  points?: number;
  email: string;
  token?: string;
  confirmationToken?: string;
  role?: Role;
  activated?: boolean;
  createdat?: Date;
  achievements?: Achievement[]
  banned?: boolean; 
};
