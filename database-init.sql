CREATE DATABASE demo;
USE demo;

#Book(ID,isbn,title,description,_author, _cover)
#Author(firstname, lastname)
#CoverImage(mimetype, imagedata)	

CREATE TABLE `books` (
  `id` int(9) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `isbn` varchar(255) NOT NULL,
  `author_fname` varchar(255) NOT NULL,
  `author_lname` varchar(255) NOT NULL,
  `publisher_company` varchar(255) NOT NULL,
  `publisher_address` varchar(255) NOT NULL,
  `cover_image` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `isbn` (`isbn`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

INSERT INTO `books` (`id`, `title`, `description`, `isbn`, `author_fname`, `author_lname`, `publisher_company`, `publisher_address`, `cover_image`) VALUES (1, 'Voluptatem illum doloremque odit dolor et.', 'Molestias est et repellendus ex omnis porro ut voluptatum. Ipsa et repellat quia voluptas dicta et ut. Repellat dolore perspiciatis est fugit dicta aliquam.', '2523665635437', 'Augustus', 'Dare', 'Runte, Mosciski and Reinger', '1245 Durward Summit Suite 978\nGutkowskiport, OR 95669-0451', 0);
INSERT INTO `books` (`id`, `title`, `description`, `isbn`, `author_fname`, `author_lname`, `publisher_company`, `publisher_address`, `cover_image`) VALUES (2, 'Veritatis omnis enim dolores aspernatur.', 'Et omnis accusamus corrupti eveniet qui repellat ut. Est eveniet labore enim eligendi. Rerum magni qui debitis accusantium magnam beatae.', '9161229548133', 'Godfrey', 'Rogahn', 'Robel, Bailey and Wunsch', '009 Brannon Squares\nGlendaland, TX 90963-2220', 0);
INSERT INTO `books` (`id`, `title`, `description`, `isbn`, `author_fname`, `author_lname`, `publisher_company`, `publisher_address`, `cover_image`) VALUES (3, 'Vitae sit autem commodi qui in quia et.', 'In suscipit omnis tempore quia molestiae expedita. Illum dolore iusto molestiae et expedita laborum.', '3524142813508', 'Esperanza', 'Hyatt', 'Brown-Swift', '6722 Carter Unions Apt. 755\nStiedemannport, WA 22904', 0);
INSERT INTO `books` (`id`, `title`, `description`, `isbn`, `author_fname`, `author_lname`, `publisher_company`, `publisher_address`, `cover_image`) VALUES (4, 'Iste distinctio consequatur dolores quisquam.', 'Autem rerum possimus ut quo quo ratione ad. Dolorem aut distinctio voluptates accusantium fugit. Repellendus aliquid et magnam dolorem omnis ea voluptate.', '9639423921648', 'Clovis', 'O\'Connell', 'Rau, Frami and Ernser', '22386 Bednar Corners\nMorarmouth, DC 12109', 0);
INSERT INTO `books` (`id`, `title`, `description`, `isbn`, `author_fname`, `author_lname`, `publisher_company`, `publisher_address`, `cover_image`) VALUES (5, 'At laborum minus aliquid.', 'Doloribus ut consequuntur excepturi necessitatibus mollitia. Voluptas reprehenderit repellendus iure odit minus est. Hic fuga perferendis est non id sequi.', '6878377526609', 'Scarlett', 'King', 'Cummerata-Gerlach', '98893 Hayes Pines\nEphraimport, ID 55684', 0);
INSERT INTO `books` (`id`, `title`, `description`, `isbn`, `author_fname`, `author_lname`, `publisher_company`, `publisher_address`, `cover_image`) VALUES (6, 'At cum tempora minima qui.', 'Neque totam consequatur rerum et dolor consequatur. Voluptates voluptatem quae expedita delectus. Quod facere sequi corrupti enim iste et. Ut aut pariatur fuga quisquam.', '9173464813408', 'Laura', 'Kozey', 'Steuber LLC', '5988 Murazik Fields Apt. 613\nDeckowborough, NE 11448', 0);
INSERT INTO `books` (`id`, `title`, `description`, `isbn`, `author_fname`, `author_lname`, `publisher_company`, `publisher_address`, `cover_image`) VALUES (7, 'In ut possimus corrupti nisi id.', 'Sed laboriosam perspiciatis quisquam doloremque. Tempora repellat quis tempora eum. Reiciendis est sit quia nam in est. Optio nostrum provident est temporibus atque. Id et illo incidunt.', '1690591564776', 'May', 'Stoltenberg', 'Ankunding-Bartell', '5344 Lubowitz Pass Apt. 211\nPollichmouth, MO 99799', 0);
INSERT INTO `books` (`id`, `title`, `description`, `isbn`, `author_fname`, `author_lname`, `publisher_company`, `publisher_address`, `cover_image`) VALUES (8, 'Sit quia aut ipsa ipsam.', 'Odio veniam consequatur minima. Perferendis et eius cupiditate expedita quasi. Ea eos quia consequuntur aut dolorem.', '2942254187546', 'Tiffany', 'Douglas', 'Corkery Ltd', '0640 Huel Oval\nNorth Gregg, ME 50380-8786', 0);
INSERT INTO `books` (`id`, `title`, `description`, `isbn`, `author_fname`, `author_lname`, `publisher_company`, `publisher_address`, `cover_image`) VALUES (9, 'Qui ex ut deleniti quos saepe cupiditate quas.', 'Ut commodi aut odit aut ipsam eos aut. Sapiente veniam et non necessitatibus eum doloribus praesentium. Tempora cum et consequatur consectetur. Accusamus et et voluptas expedita ut.', '2137111445587', 'Patricia', 'Adams', 'Rodriguez Inc', '805 Berge Point Suite 736\nSouth Chloe, VT 71659-8834', 0);
INSERT INTO `books` (`id`, `title`, `description`, `isbn`, `author_fname`, `author_lname`, `publisher_company`, `publisher_address`, `cover_image`) VALUES (10, 'Reiciendis velit facilis sapiente dolores nulla et officiis tenetur.', 'Animi doloremque aut autem magni et et unde. Pariatur quaerat deleniti ut sunt. Non fuga incidunt aspernatur inventore est. Optio commodi quaerat aut tempore omnis magnam sed deleniti.', '4196168806147', 'Carson', 'Wunsch', 'Herman-Thompson', '588 Miguel Island\nSouth Cleoton, MO 80127-4075', 0);
INSERT INTO `books` (`id`, `title`, `description`, `isbn`, `author_fname`, `author_lname`, `publisher_company`, `publisher_address`, `cover_image`) VALUES (11, 'Aliquam omnis molestias consequatur et repellendus nisi et.', 'Explicabo quo quia veniam exercitationem. Asperiores consequatur sit qui omnis. Est iusto temporibus omnis laborum culpa. Et facere animi aut omnis qui officia.', '8226574924214', 'Ramiro', 'Cassin', 'Osinski, O\'Kon and Douglas', '872 Donnelly Fort Apt. 134\nSimeonstad, OH 61521', 0);
INSERT INTO `books` (`id`, `title`, `description`, `isbn`, `author_fname`, `author_lname`, `publisher_company`, `publisher_address`, `cover_image`) VALUES (12, 'Sunt animi sed aut velit sint vero eaque quas.', 'Voluptatem cumque fugiat quidem explicabo ipsa. Non et adipisci voluptate maxime velit corporis. Adipisci nihil dolorem suscipit voluptates reiciendis omnis.', '2612817200745', 'Josefa', 'Moore', 'Frami PLC', '86602 Saige Burgs\nSouth Berniestad, MD 94123-8159', 0);
INSERT INTO `books` (`id`, `title`, `description`, `isbn`, `author_fname`, `author_lname`, `publisher_company`, `publisher_address`, `cover_image`) VALUES (13, 'Et eum eos et tempora aut.', 'Dolores aut rem et laborum autem ea incidunt velit. Nisi sed incidunt minima excepturi odio omnis consequatur quaerat.', '9590292692906', 'Alicia', 'Harris', 'Olson, Harvey and Kassulke', '533 Swaniawski Light\nTrantowmouth, VA 19635', 0);
INSERT INTO `books` (`id`, `title`, `description`, `isbn`, `author_fname`, `author_lname`, `publisher_company`, `publisher_address`, `cover_image`) VALUES (14, 'Ab autem aliquam repellendus aut.', 'Qui sit et et quibusdam corrupti officia eveniet. Fugiat ut quaerat iure enim non. Minus omnis labore quam qui quas eum.', '8244199363449', 'Shemar', 'Effertz', 'Frami Ltd', '2375 Zboncak Row\nPort Alizaborough, NH 71682-8646', 0);
INSERT INTO `books` (`id`, `title`, `description`, `isbn`, `author_fname`, `author_lname`, `publisher_company`, `publisher_address`, `cover_image`) VALUES (15, 'Ratione fugit aut sed perferendis perferendis accusamus excepturi.', 'Eveniet magni nam a repellendus possimus animi. Deleniti et aut deserunt in. Accusamus quis minus quam quo officia ex praesentium.', '9949383460947', 'Weston', 'Johns', 'Littel, Armstrong and Heidenreich', '25660 Volkman Oval Suite 920\nCollierborough, MD 59406-4781', 0);




