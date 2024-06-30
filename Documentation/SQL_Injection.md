![](/Documentation/Screenshots/SQL_Injections.png)


- By using the *PreparedStatement* function instead of the normally used *Statement* function, it allows us to use parameterized queries, which helps prevent SQL injections by treating user inputs as data not as executable code.
- The setString method is used to safely insert the user inputs (user and password) into the query. The ? placeholders in the SQL query are replaced with the actual values of this.getUser() and this.getPassword()
- The *executeQuery* method is called on the *PreparedStatement* object to execute the query. The result is stored in a ResultSet.
- And finaly we handle the resultes using an if else statement that checks if a record matching the criteria was found, using the rs.next() method.