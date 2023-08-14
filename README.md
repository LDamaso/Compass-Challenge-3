# Compass-Challenge-3
# Spring Boot Application - JSONPlaceholder API Processor

This is a Spring Boot application that interacts with an external API (JSONPlaceholder) to process posts and comments, and stores them in an H2 database.

## Technologies Used

- Java
- Spring Boot
- H2 Database
- RestTemplate

## API Endpoints

### Process Single Post
- Endpoint: `http://localhost:8080/posts/{id}`
- Method: POST
- Description: Process a single post by its ID (between 1 and 100).

### Process All Posts
- Endpoint: `http://localhost:8080/posts/process-posts`
- Method: POST
- Description: Process all posts from the external API.

### Process Single Comment
- Endpoint: `http://localhost:8080/posts/{id}/comments`
- Method: POST
- Description: Process comments for a single post by its ID.

### Process All Comments
- Endpoint: `http://localhost:8080/posts/process-comments`
- Method: POST
- Description: Process comments for all posts from the external API.

### Disable Post
- Endpoint: `http://localhost:8080/posts/disable/{id}`
- Method: DELETE
- Description: Disables a post by changing its state to "Disable".

### Enable Post
- Endpoint: `http://localhost:8080/posts/enable/{id}`
- Method: PUT
- Description: Enables a post by changing its state to "Enable".

### Query Posts
- Endpoint: `http://localhost:8080/posts/query`
- Method: GET
- Description: Retrieves a list of posts along with their comments and processing history from the database.


Make sure you have Java and Maven installed.

