create table IF NOT EXISTS book
(
  id        uuid,
  title     text,
  publisher text,
  tags      set<text>,
  primary key ((id))
);