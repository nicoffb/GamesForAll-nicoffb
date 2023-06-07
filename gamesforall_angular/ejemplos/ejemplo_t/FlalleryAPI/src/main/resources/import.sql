insert into user_entity (id, username, password, avatar, full_name, account_non_expired, account_non_locked, credentials_non_expired, enabled, created_at, last_password_change_at) values ('751c3611-8882-5751-9f0d-d216dd047ebc', 'mcampos', '{bcrypt}$2a$12$bTj0GnGvMDR2861SZP/zTedzmi5GtQZ07tM/P6ujd1O.OxpyjuNP.', 'https://icon-library.com/images/avatar-icon-images/avatar-icon-images-4.jpg', 'Miguel', true, true, true, true, '2021-09-26', '2021-09-26');
insert into user_entity (id, username, password, avatar, full_name, account_non_expired, account_non_locked, credentials_non_expired, enabled, created_at, last_password_change_at) values ('e641d9ec-ca50-4002-b87b-464b6c7686a9', 'lmlopez', '{bcrypt}$2a$12$bTj0GnGvMDR2861SZP/zTedzmi5GtQZ07tM/P6ujd1O.OxpyjuNP.', 'https://icon-library.com/images/avatar-icon-images/avatar-icon-images-4.jpg', 'Luis Miguel', true, true, true, true, '2021-09-26', '2021-09-26');

insert into user_roles(user_id,roles) values ('751c3611-8882-5751-9f0d-d216dd047ebc', '0');
insert into user_roles(user_id,roles) values ('e641d9ec-ca50-4002-b87b-464b6c7686a9', '1');

insert into artwork_category(id_category,name) values (0, 'Sin categoria');
insert into artwork_category(id_category,name) values (1, 'Esculturas');
insert into artwork_category(id_category,name) values (2, 'Cuadros');

insert into artwork (id_artwork, description, img_url, name, owner, category, disponible_para_comprar) values ('e641d9ec-ca50-4002-b87b-464b6c7686a1', 'Drama|Musical|Romance', 'p05cjdsl.jpg', 'Porgy and Bess', 'lmlopez', '1', 'True');
insert into artwork (id_artwork, description, img_url, name, owner, category, disponible_para_comprar) values ('e641d9ec-ca50-4002-b87b-464b6c7686a2', 'Horror|Thriller|Western', '223736.jpg', 'Blood River', 'lmlopez', '1', 'True');
insert into artwork (id_artwork, description, img_url, name, owner, category, disponible_para_comprar) values ('e641d9ec-ca50-4002-b87b-464b6c7686a3', 'Comedy|Horror|Mystery|Thriller', '840_560.jpg', 'Dylan Dog: Dead of Night', 'lmlopez', '1', 'True');
insert into artwork (id_artwork, description, img_url, name, owner, category, disponible_para_comprar) values ('e641d9ec-ca50-4002-b87b-464b6c7686a4', 'Comedy|Romance', '1280px-michelangelo_-_creation_of_adam_cropped-1024x465.jpg', 'Serendipity', 'lmlopez', '1', 'True');
insert into artwork (id_artwork, description, img_url, name, owner, category, disponible_para_comprar) values ('e641d9ec-ca50-4002-b87b-464b6c7686a5', 'Horror|Thriller', '1586283508_022049_1586284162_noticia_normal_recorte1.jpg', 'Loner (Woetoli)', 'mcampos', '2', 'True');
insert into artwork (id_artwork, description, img_url, name, owner, category, disponible_para_comprar) values ('e641d9ec-ca50-4002-b87b-464b6c7686a6', 'Documentary', 'arte pintura.jpg', 'Dreamworlds II: Desire, Sex, Power in Music Video', 'mcampos', '2', 'True');
insert into artwork (id_artwork, description, img_url, name, owner, category, disponible_para_comprar) values ('e641d9ec-ca50-4002-b87b-464b6c7686a7', 'Documentary', 'arte-moderno.jpg', 'Order of Myths, The', 'mcampos', '2', 'True');
insert into artwork (id_artwork, description, img_url, name, owner, category, disponible_para_comprar) values ('e641d9ec-ca50-4002-b87b-464b6c7686a8', 'Comedy|Drama|Romance', 'el-temerario-remolcado.jpg', 'Last Kiss, The (Ultimo bacio, L'')', 'mcampos', '2', 'True');

