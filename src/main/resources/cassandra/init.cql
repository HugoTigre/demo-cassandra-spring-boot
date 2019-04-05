-- Create keyspace
create keyspace if not exists essentials with replication = {'class':'SimpleStrategy', 'replication_factor':1};

-- Create book table
create table IF NOT EXISTS book
(
  id        uuid,
  title     text,
  publisher text,
  tags      set<text>,
  primary key ((id))
);

-- insert 1 book
insert into book (id, title, publisher, tags)
values (uuid(), 'Attack of the 90''s', 'your local bookstore', {'Action','Fiction'});

-- Create book_by_tag table
create table if not exists book_by_tag
(
  id_book uuid,
  title   text,
  tag     text,
  primary key ((tag), title, id_book)
) with clustering order by (title asc);

-- insert some books by tag
insert into book_by_tag (id_book, title, tag)
values (aea29849-c7a0-43d5-a9f0-69352124c5f2, 'Attack of the 90''s', 'Action') IF NOT EXISTS;
insert into book_by_tag (id_book, title, tag)
values (aea29849-c7a0-43d5-a9f0-69352124c5f2, 'Attack of the 90''s', 'Fiction') IF NOT EXISTS;
insert into book_by_tag (id_book, title, tag)
values (aea29849-c7a0-43d5-a9f0-69352124c5f2, 'Attack of the 90''s', 'Romance') IF NOT EXISTS;