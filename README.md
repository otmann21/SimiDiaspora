# SimiDiaspora
Ce projet vise à simuler un SNS sur Simgrid. Ce SNS s'inspire du fonctionnement de Diaspora, puisqu'il utilise des super peers pour coordonner le stockage et les liens entre les peers.
Il est nécessaire de créer un projet SimGrid pour pouvoir utiliser les sources présentes.

Ce fichier rassemble des idées de développement en vrac pour l'instant.

Les super peers ne different des peers que par les Process qu'ils éxécutent. Il conviendra de leur donner une plus grande puissance de calcul, et de les laisser allumés en permanence, contrairement aux peers. On peut toutefois imaginer que les peers éxécutent les process des super peers pour obtenir un SNS fully distributed.

Pour l'instant la plateforme ne contient qu'un super peer, et deux peers. C'est le minimum pour tester le comportement des process. Il va falloir développer un outil qui générera des fichiers XML platform et deployment pour tester le passage à grande echelle.
