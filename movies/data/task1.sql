-- Drop database if it exists
DROP DATABASE IF EXISTS movies;

-- Create the database
CREATE DATABASE movies;

-- Select the database
USE movies;

-- Create the attendees table
SELECT "CREATING IMDB TABLE";
CREATE TABLE imdb (

    imdb_id varchar(16),
    vote_average float default 0,
    vote_count int default 0,
    release_date date not null,
    revenue decimal(15,2) default 1000000,
    budget decimal(15,2) default 1000000,
    runtime int default 90,
    
  
    constraint pk_imdb_id primary key(imdb_id)

);


-- Grant fred access to the database
GRANT ALL PRIVILEGES ON movies.* TO 'fred'@'%';

-- Apply changes to privileges
FLUSH PRIVILEGES;