export class GlobalConstants{
    //message
    public static genericError: string = "something went wrong. Please write again later";

    //Regex 
    public static nameRegex:string = "[a-zA-Z0-9 ]*";
    public static emailRegex:string = "[A-Za-z0-9._%-]+@[A-Za-z0-9._%-]+\\.[a-z]{2,3}";

    public static productExistError:string = "Product already exists";
    public static productAdded:string ="Product added successfully";
    
    public static contactNumberRegex:string = "^[e0-9]{10,10}$";

    //variable
    public static error : string = "error";

    public static unauthorized:string = "You are not authorized perosn to access this page.";
 

}