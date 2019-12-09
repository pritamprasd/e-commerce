# Authentication Service:
### Functionality:
1. CRUD Operations on User Model: user add, remove, view-info etc. :`/users`
    To use this category of api, you need to setup root user:pass in `sync\authservice\src\main\resources\application.properties` file.
    In order to use Basic Authentication on `/users` resource.
    
    `#Replace your root username here to be used for adding users to e-com app`
     
     `root.user=<root-username-goes-here>`
     
     `#Replace your root user-password here to be used for adding users to e-com app`
     
     `root.pass=root@123`
2. Issue token on valid authentication: `/token`
3. Verify token `/validate`
 