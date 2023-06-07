
-- IMPORT USERS
-- Username: ADamas // Password: 1234Asdf // ADMIN
INSERT INTO user_entity (id, account_non_expired, account_non_locked, avatar, birthday, created_at, credentials_non_expired, description, email, enabled, fullname, last_password_change_at, password, username) VALUES ('ac132001-865b-1a5b-8186-5b5bba320000', true, true, 'peepoSad3.jpg', '1998-01-10', '2023-02-16 18:53:03.527092', true, 'Usuario admin para controlarlo todo y tal', 'damas.viale23@triana.salesianos.edu', true, 'Alejandro Damas', '2023-02-16 18:53:03.499871', '{bcrypt}$2a$10$JgJe3NS9U68X3mZzt73gZeJJncSWw9nd42BXUryyMrvwd9CbBxgS2', 'ADamas');
-- Username: AleUser // Password: 1234Asdf // USER
INSERT INTO user_entity (id, account_non_expired, account_non_locked, avatar, birthday, created_at, credentials_non_expired, description, email, enabled, fullname, last_password_change_at, password, username) VALUES ('ac132001-865b-1a5b-8186-5b5bc7710001', true, true, 'peepoSad3.jpg', '1998-01-10', '2023-02-16 18:53:06.929198', true, 'Usuario user para hacer pruebas y tal', 'alejandrodamas3d@gmail.com', true, 'Alejandro Damas', '2023-02-16 18:53:06.929198', '{bcrypt}$2a$10$KwbG6tq/ZzK/8/gzO2cJO.peKDy1ZWnvcdJSAOSfEpH3h5QVXwlfK', 'AleUser');

-- IMPORT ROLES
INSERT INTO user_roles (user_id, roles) VALUES ('ac132001-865b-1a5b-8186-5b5bba320000', 0);
INSERT INTO user_roles (user_id, roles) VALUES ('ac132001-865b-1a5b-8186-5b5bc7710001', 1);


-- IMPORT TYPES
INSERT INTO type (id, name) VALUES ('ac1f6001-87f6-12bb-8187-f6c2e6b90000', 'breakfast');
INSERT INTO type (id, name) VALUES ('ac1f6001-87f6-12bb-8187-f6c2fb4d0001', 'dinner');
INSERT INTO type (id, name) VALUES ('ac1f6001-87f6-12bb-8187-f6c3133e0002', 'lunch');
INSERT INTO type (id, name) VALUES ('ac1f6001-87f6-12bb-8187-f6c943ab0003', 'snacks');

-- IMPORT CATEGORIES
INSERT INTO category (id, color_code, name) VALUES ('250cf64d-ddb3-47d5-a146-773206da6f72', '#FFFFFF', 'vegetarian');
INSERT INTO category (id, color_code, name) VALUES ('a76d2e0a-2b9f-4026-84fb-d2ce70073a06', '#FFFFFF', 'vegan');
INSERT INTO category (id, color_code, name) VALUES ('ef74286f-475b-42ed-a3ba-c9ae67f8717a', '#FFFFFF', 'low-carbs');
INSERT INTO category (id, color_code, name) VALUES ('d977f780-73a1-405c-8171-fb51b513a35e', '#FFFFFF', 'no lactose');

-- IMPORT INGREDIENTS
INSERT INTO ingredient (id, description, img, name, author_id) VALUES ('ac1f6001-87f6-12bb-8187-f6d575800005', 'Alimento base en la alimentacion como gran fuente de proteinas. Es bastante versatil', 'default_ingredient.jpg', 'Huevo', 'ac132001-865b-1a5b-8186-5b5bba320000');
INSERT INTO ingredient (id, description, img, name, author_id) VALUES ('ac1f6001-87f6-12bb-8187-f6d680900006', 'Tuberculo proveniente de la planta de la patata que es una gran fuente de hidratos de carbono', 'default_ingredient.jpg', 'Patata', 'ac132001-865b-1a5b-8186-5b5bba320000');
INSERT INTO ingredient (id, description, img, name, author_id) VALUES ('ac1f6001-87f6-12bb-8187-f6d7d5640007', 'Aceite vegetal proveniente del fruto de la oliva o aceituna, usado principalmente en la cocina europea', 'default_ingredient.jpg', 'Aceite de oliva', 'ac132001-865b-1a5b-8186-5b5bba320000');
INSERT INTO ingredient (id, description, img, name, author_id) VALUES ('ac1f6001-87f6-12bb-8187-f6d99e930008', 'Cereal formado mayormente por hidratos de carbono, siendo el que mas cantidad de proteinas presenta. Bueno contra enfermedades cardiovasculares como el exceso de colesterol', 'default_ingredient.jpg', 'Avena', 'ac132001-865b-1a5b-8186-5b5bba320000');
INSERT INTO ingredient (id, description, img, name, author_id) VALUES ('ac1f6001-87f6-12bb-8187-f6db80210009', 'Bebida elaborada a partir de los granos de soja con alto contenido en vitaminas A, E y proteinas', 'default_ingredient.jpg', 'Leche de soja', 'ac132001-865b-1a5b-8186-5b5bba320000');
INSERT INTO ingredient (id, description, img, name, author_id) VALUES ('ac1f6001-87f6-12bb-8187-f6dc6833000a', 'Fruta tropical procendente de la planta con mismo nombrem. Es una gran fuente de potasio', 'default_ingredient.jpg', 'Platano', 'ac132001-865b-1a5b-8186-5b5bba320000');
INSERT INTO ingredient (id, description, img, name, author_id) VALUES ('ac1f6001-87f6-12bb-8187-f6dd4fc6000b', 'Yogur de sabor chocolate con alta cantidad de proteina que se encuentra en el supermercado Mercadona', 'default_ingredient.jpg', 'Yogur chocolate proteico, Mercadona', 'ac132001-865b-1a5b-8186-5b5bba320000');
INSERT INTO ingredient (id, description, img, name, author_id) VALUES ('ac1f6001-87f6-12bb-8187-f6df105b000c', 'Crema realizada a base de triturar cacahuetes, con alta cantidad de grasas y proteinas', 'default_ingredient.jpg', 'Crema de cacahuete', 'ac132001-865b-1a5b-8186-5b5bba320000');
INSERT INTO ingredient (id, description, img, name, author_id) VALUES ('ac1f6001-87f6-12bb-8187-f6dfe9ca000d', 'Pequeños trozos de almendra crujientes', 'default_ingredient.jpg', 'Trozos de almendra crocante', 'ac132001-865b-1a5b-8186-5b5bba320000');
INSERT INTO ingredient (id, description, img, name, author_id) VALUES ('ac1f6001-87f6-12bb-8187-f6f0e4f3000e', 'Es un ingrediente muy presente en nuestra forma de cocinar y es excelente para dar textura a nuestros platos, elaborar salsas o cremas, siendo un elemento esencial en miles de recetas de platos dulces y salados.', 'default_ingredient.jpg', 'Nata', 'ac132001-865b-1a5b-8186-5b5bba320000');
INSERT INTO ingredient (id, description, img, name, author_id) VALUES ('ac1f6001-87f6-12bb-8187-f6f1b0ef000f', 'Producto lácteo elaborado a partir de la leche de vaca. Básicamente, es la parte grasa de la leche, la cual ha sido aislada de la proteína y de los carbohidratos. Es sabrosa y suele ser utilizada para cocinar, hornear y untar el pan.', 'default_ingredient.jpg', 'Mantequilla', 'ac132001-865b-1a5b-8186-5b5bba320000');
INSERT INTO ingredient (id, description, img, name, author_id) VALUES ('ac1f6001-87f6-12bb-8187-f6f30ba30010', 'Es una especia poderosa y cargada de beneficios, más allá del exquisito aroma o el sabor que introducen en tus platos.', 'default_ingredient.jpg', 'Pimienta negra', 'ac132001-865b-1a5b-8186-5b5bba320000');
INSERT INTO ingredient (id, description, img, name, author_id) VALUES ('ac1f6001-87f6-12bb-8187-f6f477be0011', 'Mineral utilizado como conservante, para deshidratar alimentos, para enmascarar sabores desagradables, para facilitar la retención de agua o, simplemente, para hacer al alimento más sabroso.', 'default_ingredient.jpg', 'Sal', 'ac132001-865b-1a5b-8186-5b5bba320000');
INSERT INTO ingredient (id, description, img, name, author_id) VALUES ('ac1f6001-87f6-12bb-8187-f6f68abb0012', 'Alimento perteneciente a los hidratos de carbono, de color blanco y que es usado mayormente para endulzar platos', 'default_ingredient.jpg', 'Azucar', 'ac132001-865b-1a5b-8186-5b5bba320000');
INSERT INTO ingredient (id, description, img, name, author_id) VALUES ('ac1f6001-87f6-12bb-8187-f6f727250013', 'Liquido resultado de exprimir limones', 'default_ingredient.jpg', 'Jugo de limon', 'ac132001-865b-1a5b-8186-5b5bba320000');
INSERT INTO ingredient (id, description, img, name, author_id) VALUES ('ac1f6001-87f6-12bb-8187-f6fa67f70014', 'Bebida blanquecina producida por las vacas. Es una gran fuente de calcio.', 'default_ingredient.jpg', 'Leche de vaca', 'ac132001-865b-1a5b-8186-5b5bba320000');
INSERT INTO ingredient (id, description, img, name, author_id) VALUES ('ac1f6001-87f6-12bb-8187-f6fd9b220015', 'Proteina pura proveniente de animales, usada para dar consistencia en las recetas', 'default_ingredient.jpg', 'Gelatina', 'ac132001-865b-1a5b-8186-5b5bba320000');
INSERT INTO ingredient (id, description, img, name, author_id) VALUES ('ac1f6001-87f6-12bb-8187-f6ffc95b0016', 'Galletas de forma redondeada de bajo precio, y relativo bajo contenido en grasa y azucar', 'default_ingredient.jpg', 'Galletas Maria', 'ac132001-865b-1a5b-8186-5b5bba320000');
INSERT INTO ingredient (id, description, img, name, author_id) VALUES ('ac1f6001-87f6-12bb-8187-f7014ef00017', 'Queso sin corteza, de pasta blanda, con una consistencia cremosa muy fácil de extender y de fundir para acompañar cualquier aperitivo', 'default_ingredient.jpg', 'Queso Philadelphia', 'ac132001-865b-1a5b-8186-5b5bba320000');
INSERT INTO ingredient (id, description, img, name, author_id) VALUES ('ac1f6001-87f6-12bb-8187-f702a5f90018', 'Producto preparado por cocción de fresas, troceadas, trituradas, tamizadas o no, a las que se han incorporado azúcares hasta conseguir un producto semilíquido o espeso.', 'default_ingredient.jpg', 'Mermelada de fresa', 'ac132001-865b-1a5b-8186-5b5bba320000');

-- IMPORT RECIPES
INSERT INTO recipe (id, created_at, description, img, name, prep_time, author_id, type_id) VALUES ('ac1f6001-87f6-12bb-8187-f712a8c40019', '2023-05-07 18:36:47.935953', 'Una cosa es hacer un huevo frito y otra cosa es hacer un huevo frito perfecto. Entendemos por huevo frito perfecto aquel que tiene su clara completamente cuajada, su yema perfectamente líquida y una preciosa puntilla a su alrededor.', 'default_recipe.jpg', 'Huevos fritos perfectos', 1, 'ac132001-865b-1a5b-8186-5b5bba320000', 'ac1f6001-87f6-12bb-8187-f6c3133e0002');
INSERT INTO recipe (id, created_at, description, img, name, prep_time, author_id, type_id) VALUES ('ac1f6001-87f6-12bb-8187-f71acec6001f', '2023-05-07 18:45:41.958665', 'Al igual que la tortilla de patatas, esta es una de las recetas que más se cocinan en las casas de nuestro país. Una receta muy sencilla, perfecta para una cena rápida cuando nos falta tiempo o ideas para algo más elaborado.', 'default_recipe.jpg', 'Tortilla francesa', 15, 'ac132001-865b-1a5b-8186-5b5bba320000', 'ac1f6001-87f6-12bb-8187-f6c2fb4d0001');

-- IMPORT RECIPE_CATEGORIES
INSERT INTO recipe_category (recipe_id, category_id) VALUES ('ac1f6001-87f6-12bb-8187-f712a8c40019', 'a76d2e0a-2b9f-4026-84fb-d2ce70073a06');
INSERT INTO recipe_category (recipe_id, category_id) VALUES ('ac1f6001-87f6-12bb-8187-f712a8c40019', 'ef74286f-475b-42ed-a3ba-c9ae67f8717a');
INSERT INTO recipe_category (recipe_id, category_id) VALUES ('ac1f6001-87f6-12bb-8187-f712a8c40019', 'd977f780-73a1-405c-8171-fb51b513a35e');
INSERT INTO recipe_category (recipe_id, category_id) VALUES ('ac1f6001-87f6-12bb-8187-f71acec6001f', 'ef74286f-475b-42ed-a3ba-c9ae67f8717a');
INSERT INTO recipe_category (recipe_id, category_id) VALUES ('ac1f6001-87f6-12bb-8187-f71acec6001f', '250cf64d-ddb3-47d5-a146-773206da6f72');

-- IMPORT RECIPE_INGREDIENTS
INSERT INTO recipe_ingredient (id_ingredient, id_recipe, amount, unit) VALUES ('ac1f6001-87f6-12bb-8187-f6d575800005', 'ac1f6001-87f6-12bb-8187-f712a8c40019', 1, 'unit');
INSERT INTO recipe_ingredient (id_ingredient, id_recipe, amount, unit) VALUES ('ac1f6001-87f6-12bb-8187-f6d7d5640007', 'ac1f6001-87f6-12bb-8187-f712a8c40019', 1, 'unit');
INSERT INTO recipe_ingredient (id_ingredient, id_recipe, amount, unit) VALUES ('ac1f6001-87f6-12bb-8187-f6f477be0011', 'ac1f6001-87f6-12bb-8187-f712a8c40019', 1, 'grams');
INSERT INTO recipe_ingredient (id_ingredient, id_recipe, amount, unit) VALUES ('ac1f6001-87f6-12bb-8187-f6d575800005', 'ac1f6001-87f6-12bb-8187-f71acec6001f', 3, 'unit');
INSERT INTO recipe_ingredient (id_ingredient, id_recipe, amount, unit) VALUES ('ac1f6001-87f6-12bb-8187-f6f0e4f3000e', 'ac1f6001-87f6-12bb-8187-f71acec6001f', 5, 'ml');
INSERT INTO recipe_ingredient (id_ingredient, id_recipe, amount, unit) VALUES ('ac1f6001-87f6-12bb-8187-f6f1b0ef000f', 'ac1f6001-87f6-12bb-8187-f71acec6001f', 3, 'cubes');
INSERT INTO recipe_ingredient (id_ingredient, id_recipe, amount, unit) VALUES ('ac1f6001-87f6-12bb-8187-f6f30ba30010', 'ac1f6001-87f6-12bb-8187-f71acec6001f', 1, 'unit');
INSERT INTO recipe_ingredient (id_ingredient, id_recipe, amount, unit) VALUES ('ac1f6001-87f6-12bb-8187-f6f477be0011', 'ac1f6001-87f6-12bb-8187-f71acec6001f', 1, 'pinch');

-- IMPORT STEPS
INSERT INTO step (id, description, step_number, recipe_id) VALUES ('ac1f6001-87f6-12bb-8187-f71685d1001a', 'Abrir el huevo sobre un cuenco', 1, 'ac1f6001-87f6-12bb-8187-f712a8c40019');
INSERT INTO step (id, description, step_number, recipe_id) VALUES ('ac1f6001-87f6-12bb-8187-f716cf2d001b', 'Añadir el aceite a la sartén hasta cubrir el fondo. Poner a calentar la sartén a fuego alto', 2, 'ac1f6001-87f6-12bb-8187-f712a8c40019');
INSERT INTO step (id, description, step_number, recipe_id) VALUES ('ac1f6001-87f6-12bb-8187-f71762c8001c', 'Cuando el aceite esté caliente, pero sin humear, añadir el huevo. Dejar que se vaya formando la puntilla. ', 3, 'ac1f6001-87f6-12bb-8187-f712a8c40019');
INSERT INTO step (id, description, step_number, recipe_id) VALUES ('ac1f6001-87f6-12bb-8187-f717ba7e001d', 'A continuación, inclinar un poco la sartén y dejar que el aceite caliente suba por la superficie del huevo sin que alcance la yema, para cocinar la parte superior. ', 4, 'ac1f6001-87f6-12bb-8187-f712a8c40019');
INSERT INTO step (id, description, step_number, recipe_id) VALUES ('ac1f6001-87f6-12bb-8187-f717d7de001e', 'Cuando toda la parte superior esté blanca, excepto la yema, retirar de la sartén. Añadir sal a la yema y servir inmediatamente', 5, 'ac1f6001-87f6-12bb-8187-f712a8c40019');
INSERT INTO step (id, description, step_number, recipe_id) VALUES ('ac1f6001-87f6-12bb-8187-f72093340020', 'Batir los huevos, salpimentados y añadir un poco de nata fresca (opcional)', 1, 'ac1f6001-87f6-12bb-8187-f71acec6001f');
INSERT INTO step (id, description, step_number, recipe_id) VALUES ('ac1f6001-87f6-12bb-8187-f720b5d60021', 'Calentar la sartén a fuego medio y añadir unos dados de mantequilla', 2, 'ac1f6001-87f6-12bb-8187-f71acec6001f');
INSERT INTO step (id, description, step_number, recipe_id) VALUES ('ac1f6001-87f6-12bb-8187-f720dfc00022', 'Cuando chisporrotee la mantequilla añadimos los huevos batidos y bajamos la temperatura del fuego al mínimo', 3, 'ac1f6001-87f6-12bb-8187-f71acec6001f');
INSERT INTO step (id, description, step_number, recipe_id) VALUES ('ac1f6001-87f6-12bb-8187-f721024f0023', 'Removemos los huevos sin dejar que se pegue al fondo hasta conseguir un revuelto cremoso', 4, 'ac1f6001-87f6-12bb-8187-f71acec6001f');
INSERT INTO step (id, description, step_number, recipe_id) VALUES ('ac1f6001-87f6-12bb-8187-f72120fe0024', 'Dejamos de remover y cuando vaya cuajando el fondo levantamos uno de los lados', 5, 'ac1f6001-87f6-12bb-8187-f71acec6001f');
INSERT INTO step (id, description, step_number, recipe_id) VALUES ('ac1f6001-87f6-12bb-8187-f7214fdf0025', 'Damos unos golpecitos a la sartén para que se nos levante el otro lado y cerramos la tortilla con cuidado hacia el centro y los lados', 6, 'ac1f6001-87f6-12bb-8187-f71acec6001f');
INSERT INTO step (id, description, step_number, recipe_id) VALUES ('ac1f6001-87f6-12bb-8187-f72171840026', 'Damos vuelta para que selle brevemente y servimos', 7, 'ac1f6001-87f6-12bb-8187-f71acec6001f');

-- IMPORT FAVORITES
INSERT INTO favorites (user_id, recipe_id) VALUES ('ac132001-865b-1a5b-8186-5b5bba320000', 'ac1f6001-87f6-12bb-8187-f712a8c40019');
INSERT INTO favorites (user_id, recipe_id) VALUES ('ac132001-865b-1a5b-8186-5b5bba320000', 'ac1f6001-87f6-12bb-8187-f71acec6001f');
