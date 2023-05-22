export interface ILoginForm {
    accountName: string,
    password: string,
}

export class LoginForm {
    loginForm: ILoginForm = {
        accountName: '',
        password: '',
    }
    password: string = '';
}