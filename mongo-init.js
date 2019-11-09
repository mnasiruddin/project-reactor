db.createUser(
    {
      user: "user",
      pwd: "userpassword",
      roles: [
        {
          role: "readWrite",
          db: "admindatabase"
        }
      ]
    }
);