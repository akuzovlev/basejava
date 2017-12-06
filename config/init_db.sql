CREATE TABLE resume (
  uuid      CHAR(36) PRIMARY KEY NOT NULL,
  full_name TEXT                 NOT NULL
);

CREATE TABLE contact (
  id          SERIAL,
  resume_uuid CHAR(36) NOT NULL REFERENCES resume (uuid) ON DELETE CASCADE,
  type        TEXT     NOT NULL,
  value       TEXT     NOT NULL
);

CREATE UNIQUE INDEX contact_uuid_type_index
  ON contact (resume_uuid, type);

create table section
(
  id serial not null
    constraint sections_pkey
    primary key,
  type text not null,
  value text,
  resume_uuid char(36) not null
    constraint section_resume_uuid_fk
    references resume
    on delete cascade
)
;

