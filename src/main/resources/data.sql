INSERT INTO state (id, state_name) VALUES
                                       (1, 'Alabama'),
                                       (2, 'Alaska'),
                                       (3, 'Arizona'),
                                       (4, 'Arkansas'),
                                       (5, 'California'),
                                       (6, 'Colorado'),
                                       (7, 'Connecticut'),
                                       (8, 'Delaware'),
                                       (9, 'Florida'),
                                       (10, 'Georgia'),
                                       (11, 'Hawaii'),
                                       (12, 'Idaho'),
                                       (13, 'Illinois'),
                                       (14, 'Indiana'),
                                       (15, 'Iowa'),
                                       (16, 'Kansas'),
                                       (17, 'Kentucky'),
                                       (18, 'Louisiana'),
                                       (19, 'Maine'),
                                       (20, 'Maryland'),
                                       (21, 'Massachusetts'),
                                       (22, 'Michigan'),
                                       (23, 'Minnesota'),
                                       (24, 'Mississippi'),
                                       (25, 'Missouri'),
                                       (26, 'Montana'),
                                       (27, 'Nebraska'),
                                       (28, 'Nevada'),
                                       (29, 'New Hampshire'),
                                       (30, 'New Jersey'),
                                       (31, 'New Mexico'),
                                       (32, 'New York'),
                                       (33, 'North Carolina'),
                                       (34, 'North Dakota'),
                                       (35, 'Ohio'),
                                       (36, 'Oklahoma'),
                                       (37, 'Oregon'),
                                       (38, 'Pennsylvania'),
                                       (39, 'Rhode Island'),
                                       (40, 'South Carolina'),
                                       (41, 'South Dakota'),
                                       (42, 'Tennessee'),
                                       (43, 'Texas'),
                                       (44, 'Utah'),
                                       (45, 'Vermont'),
                                       (46, 'Virginia'),
                                       (47, 'Washington'),
                                       (48, 'West Virginia'),
                                       (49, 'Wisconsin'),
                                       (50, 'Wyoming');
INSERT INTO city (id, state_id, city_name) VALUES
                                               (1, 1, 'Birmingham'), -- Alabama
                                               (2, 1, 'Montgomery'),
                                               (3, 2, 'Anchorage'), -- Alaska
                                               (4, 3, 'Phoenix'), -- Arizona
                                               (5, 3, 'Tucson'),
                                               (6, 4, 'Little Rock'), -- Arkansas
                                               (7, 5, 'Los Angeles'), -- California
                                               (8, 5, 'San Francisco'),
                                               (9, 6, 'Denver'), -- Colorado
                                               (10, 6, 'Colorado Springs'),
                                               (11, 7, 'Hartford'), -- Connecticut
                                               (12, 8, 'Wilmington'), -- Delaware
                                               (13, 9, 'Miami'), -- Florida
                                               (14, 9, 'Orlando'),
                                               (15, 10, 'Atlanta'), -- Georgia
                                               (16, 10, 'Savannah'),
                                               (17, 11, 'Honolulu'), -- Hawaii
                                               (18, 12, 'Boise'), -- Idaho
                                               (19, 13, 'Chicago'), -- Illinois
                                               (20, 13, 'Springfield'),
                                               (21, 14, 'Indianapolis'), -- Indiana
                                               (22, 15, 'Des Moines'), -- Iowa
                                               (23, 16, 'Wichita'), -- Kansas
                                               (24, 17, 'Louisville'), -- Kentucky
                                               (25, 17, 'Lexington'),
                                               (26, 18, 'New Orleans'), -- Louisiana
                                               (27, 19, 'Portland'), -- Maine
                                               (28, 20, 'Baltimore'), -- Maryland
                                               (29, 21, 'Boston'), -- Massachusetts
                                               (30, 21, 'Cambridge'),
                                               (31, 22, 'Detroit'), -- Michigan
                                               (32, 23, 'Minneapolis'), -- Minnesota
                                               (33, 24, 'Jackson'), -- Mississippi
                                               (34, 25, 'Kansas City'), -- Missouri
                                               (35, 25, 'St. Louis'),
                                               (36, 26, 'Billings'), -- Montana
                                               (37, 27, 'Omaha'), -- Nebraska
                                               (38, 28, 'Las Vegas'), -- Nevada
                                               (39, 29, 'Manchester'), -- New Hampshire
                                               (40, 30, 'Newark'), -- New Jersey
                                               (41, 31, 'Albuquerque'), -- New Mexico
                                               (42, 32, 'New York City'), -- New York
                                               (43, 32, 'Buffalo'),
                                               (44, 33, 'Charlotte'), -- North Carolina
                                               (45, 33, 'Raleigh'),
                                               (46, 34, 'Fargo'), -- North Dakota
                                               (47, 35, 'Columbus'), -- Ohio
                                               (48, 36, 'Oklahoma City'), -- Oklahoma
                                               (49, 37, 'Portland'), -- Oregon
                                               (50, 38, 'Philadelphia'), -- Pennsylvania
                                               (51, 39, 'Providence'), -- Rhode Island
                                               (52, 40, 'Charleston'), -- South Carolina
                                               (53, 41, 'Sioux Falls'), -- South Dakota
                                               (54, 42, 'Nashville'), -- Tennessee
                                               (55, 43, 'Houston'), -- Texas
                                               (56, 43, 'Dallas'),
                                               (57, 44, 'Salt Lake City'), -- Utah
                                               (58, 45, 'Burlington'), -- Vermont
                                               (59, 46, 'Virginia Beach'), -- Virginia
                                               (60, 47, 'Seattle'), -- Washington
                                               (61, 48, 'Charleston'), -- West Virginia
                                               (62, 49, 'Milwaukee'), -- Wisconsin
                                               (63, 50, 'Cheyenne'); -- Wyoming

INSERT INTO make (make_id, make_name) VALUES
                                          (1, 'Toyota'),
                                          (2, 'Ford'),
                                          (3, 'Chevrolet'),
                                          (4, 'Honda'),
                                          (5, 'Nissan'),
                                          (6, 'BMW'),
                                          (7, 'Mercedes-Benz'),
                                          (8, 'Volkswagen'),
                                          (9, 'Hyundai'),
                                          (10, 'Kia'),
                                          (11, 'Audi'),
                                          (12, 'Lexus'),
                                          (13, 'Subaru'),
                                          (14, 'Mazda'),
                                          (15, 'Dodge'),
                                          (16, 'Jeep'),
                                          (17, 'GMC'),
                                          (18, 'Ram'),
                                          (19, 'Cadillac'),
                                          (20, 'Chrysler'),
                                          (21, 'Buick'),
                                          (22, 'Volvo'),
                                          (23, 'Mitsubishi'),
                                          (24, 'Land Rover'),
                                          (25, 'Jaguar'),
                                          (26, 'Infiniti'),
                                          (27, 'Acura'),
                                          (28, 'Lincoln'),
                                          (29, 'Tesla'),
                                          (30, 'Porsche'),
                                          (31, 'Mini'),
                                          (32, 'Fiat'),
                                          (33, 'Alfa Romeo'),
                                          (34, 'Genesis'),
                                          (35, 'Maserati'),
                                          (36, 'Ferrari'),
                                          (37, 'Lamborghini'),
                                          (38, 'Bentley'),
                                          (39, 'Rolls-Royce'),
                                          (40, 'Aston Martin'),
                                          (41, 'McLaren'),
                                          (42, 'Bugatti'),
                                          (43, 'Pagani'),
                                          (44, 'Suzuki'),
                                          (45, 'Peugeot'),
                                          (46, 'Renault'),
                                          (47, 'Skoda'),
                                          (48, 'Seat'),
                                          (49, 'Citroen'),
                                          (50, 'Opel');

INSERT INTO car_model (make_id, model_id, model_name) VALUES (1, 1, 'Camry'),
(1, 2, 'Corolla'),
(1, 3, 'RAV4'),
(1, 4, 'Highlander'),
(1, 5, 'Prius'),
(2, 6, 'F-150'), -- Ford Models
(2, 7, 'Mustang'),
(2, 8, 'Explorer'),
(2, 9, 'Escape'),
(2, 10, 'Edge'),
(3, 11, 'Silverado'),-- Chevrolet Models
(3, 12, 'Malibu'),
(3, 13, 'Equinox'),
(3, 14, 'Tahoe'),
(3, 15, 'Camaro'),
(4, 16, 'Civic'),-- Honda Models
(4, 17, 'Accord'),
(4, 18, 'CR-V'),
(4, 19, 'Pilot'),
(4, 20, 'Fit'),
(5, 21, 'Altima'),-- Nissan Models
(5, 22, 'Sentra'),
(5, 23, 'Maxima'),
(5, 24, 'Rogue'),
(5, 25, 'Murano'),
(6, 26, '3 Series'),-- BMW Models
(6, 27, '5 Series'),
(6, 28, 'X3'),
(6, 29, 'X5'),
(6, 30, '7 Series'),
(7, 31, 'C-Class'),-- Mercedes-Benz Models
(7, 32, 'E-Class'),
(7, 33, 'S-Class'),
(7, 34, 'GLC'),
(7, 35, 'GLE'),
(8, 36, 'Golf'),-- Volkswagen Models
(8, 37, 'Passat'),
(8, 38, 'Tiguan'),
(8, 39, 'Jetta'),
(8, 40, 'Atlas'),
(9, 41, 'Elantra'),-- Hyundai Models
(9, 42, 'Santa Fe'),
(9, 43, 'Tucson'),
(9, 44, 'Sonata'),
(9, 45, 'Kona'),
(10, 46, 'Sorento'),-- Kia Models
(10, 47, 'Sportage'),
(10, 48, 'Optima'),
(10, 49, 'Soul'),
(10, 50, 'Telluride'),
(11, 51, 'A3'),-- Audi Models
(11, 52, 'A4'),
(11, 53, 'Q5'),
(11, 54, 'Q7'),
(11, 55, 'A6'),
(12, 56, 'RX'),-- Lexus Models
(12, 57, 'ES'),
(12, 58, 'NX'),
(12, 59, 'GX'),
(12, 60, 'IS'),
(13, 61, 'Outback'),-- Subaru Models
(13, 62, 'Forester'),
(13, 63, 'Impreza'),
(13, 64, 'Crosstrek'),
(13, 65, 'Ascent'),
(14, 66, 'Mazda3'),-- Mazda Models
(14, 67, 'Mazda6'),
(14, 68, 'CX-5'),
(14, 69, 'CX-9'),
(14, 70, 'MX-5 Miata'),
(15, 71, 'Charger'),-- Dodge Models
(15, 72, 'Challenger'),
(15, 73, 'Durango'),
(15, 74, 'Journey'),
(15, 75, 'Grand Caravan'),
(16, 76, 'Wrangler'),-- Jeep Models
(16, 77, 'Grand Cherokee'),
(16, 78, 'Cherokee'),
(16, 79, 'Renegade'),
(16, 80, 'Compass'),
(29, 81, 'CarModel S'),-- Tesla Models
(29, 82, 'CarModel 3'),
(29, 83, 'CarModel X'),
(29, 84, 'CarModel Y'),
(30, 85, '911'),-- Porsche Models
(30, 86, 'Cayenne'),
(30, 87, 'Macan'),
(30, 88, 'Panamera'),
(30, 89, 'Taycan'),
(24, 90, 'Range Rover'),-- Land Rover Models
(24, 91, 'Discovery'),
(24, 92, 'Defender'),
(24, 93, 'Range Rover Sport'),
(24, 94, 'Range Rover Evoque');

insert into users (user_id,email, first_name, last_name, password, role)
values (1,'admin@gmail.com','john','smith','$2a$10$tZPw6NeeEBCh7w/CLhlVr.cY6x0SWnOipKXrMMBIJSwo8ckydbyFa','ADMIN');
SELECT setval('users_user_id_seq', 2, false);
