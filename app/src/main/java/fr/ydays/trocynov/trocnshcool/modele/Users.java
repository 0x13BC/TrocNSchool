package fr.ydays.trocynov.trocnshcool.modele;

public class Users {
    public String Id;
    public String Nom;
    public String Prenom;
    public String Email;
    public String Password;
   public Users(String id,String nom,String prenom,String email,String password )
   {
       this.Id= id;
       this.Nom=nom;
       this.Prenom=prenom;
       this.Email= email;
       this.Password= password;


   }
}
