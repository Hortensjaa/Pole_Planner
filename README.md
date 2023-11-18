# Pole Dance Training Assistant

Aplikacja została napisana w języku Kotlin przy użyciu frameworku Jetpack Compose i przeznaczona
jest dla systemu Android. 
Jest dedykowana dla osób trenujących pole dance, mając na celu wspieranie treningów w tej dziedzinie. 
Ze względu na wybór grupy docelowej, interfejs aplikacji jest w języku polskim.

## Opis Projektu

Projekt składa się z kilku głównych modułów:

### Data Structure

Moduł [`data_structure`](https://github.com/Hortensjaa/Pole_Planner/tree/master/app/src/main/java/com/example/poleplanner/data_structure) 
stanowi rdzeń aplikacji, zawierając wszystkie elementy związane z zarządzaniem danymi w bazie. 
Głównym celem tego modułu jest obsługa bazy danych opartej na Room, 
która przechowuje kluczowe informacje dotyczące figur pole dance. 
Centralną tabelą jest `Pose`, zawierająca nazwy figur, zdjęcia, poziomy trudności i [tagi](https://github.com/Hortensjaa/Pole_Planner/blob/master/app/src/main/java/com/example/poleplanner/data_structure/models/Tag.kt). 
Dodatkowo, ta tabela gromadzi dane użytkownika, takie jak poziom opanowania figury czy osobiste notatki. 
Moduł ten zawiera podfoldery:

- (**daos**)[https://github.com/Hortensjaa/Pole_Planner/tree/master/app/src/main/java/com/example/poleplanner/data_structure/daos]: Interfejsy do komunikacji z bazą danych.
- (**models**)[https://github.com/Hortensjaa/Pole_Planner/tree/master/app/src/main/java/com/example/poleplanner/data_structure/models]: Definicje struktur danych używanych w bazie.
- (**references**)[https://github.com/Hortensjaa/Pole_Planner/tree/master/app/src/main/java/com/example/poleplanner/data_structure/references]: Referencje tworzące relacje między strukturami danych.
oraz pliki zawierające 
(konwertery)[https://github.com/Hortensjaa/Pole_Planner/blob/master/app/src/main/java/com/example/poleplanner/data_structure/InitialData.kt], 
(dane początkowe)[https://github.com/Hortensjaa/Pole_Planner/tree/master/app/src/main/java/com/example/poleplanner/data_structure/InitialData.kt],
domyślnie znajdujące się w bazie, oraz (implementację samej bazy.)[https://github.com/Hortensjaa/Pole_Planner/blob/master/app/src/main/java/com/example/poleplanner/data_structure/Database.kt]

### Navbar

W module (`navbar`)[https://github.com/Hortensjaa/Pole_Planner/tree/master/app/src/main/java/com/example/poleplanner/navbar] 
znajduje się logika nawigacji między ekranami mojej aplikacji oraz implementacja wysuwanego menu. 
Ten moduł umożliwia użytkownikowi wygodne poruszanie się po aplikacji.

### Moduły ekranów
Kolejne moduły związane są z poszczególnymi ekranami interfejsu aplikacji. 
Każdy z nich zawiera podfolder `composables` z kompozytami napisanymi z użyciem Jetpack Compose. 
Główny ekran zawsze znajduje się w pliku o nazwie postaci '[nazwa widoku]'Screen.kt. 
Każdy widok ma dedykowany ViewModel znajdujący się poza podfolderem `composables`.

#### (Pose Adding View)[https://github.com/Hortensjaa/Pole_Planner/tree/master/app/src/main/java/com/example/poleplanner/pose_adding_view]

Moduł `pose_adding_view` pozwala użytkownikowi na dodanie nowej figury do bazy danych. 
Jeśli dana figura jeszcze nie istnieje, użytkownik może ją wprowadzić, 
zapewniając unikalność i elastyczność listy treningowej.

#### (Pose Detail View)[https://github.com/Hortensjaa/Pole_Planner/tree/master/app/src/main/java/com/example/poleplanner/pose_detail_view]

W `pose_detail_view` użytkownik może sprawdzić szczegóły wybranej figury pole dance, takie jak jej opis i poziom trudności. 
To miejsce umożliwia wprowadzenie zmian w edytowalnych polach takich jak notatki i poziom opanowania figury. 
Użytkownik ma także możliwość edycji i usuwania figur dodanych przez siebie.

#### (Pose of a Day)[https://github.com/Hortensjaa/Pole_Planner/tree/master/app/src/main/java/com/example/poleplanner/pose_of_a_day]

Dodatkową funkcjonalnością aplikacji jest codzienne losowanie "figury dnia", która wyświetlana 
jest na głównym (początkowym) ekranie aplikacji. . 
Ta funkcjonalność motywuje użytkowników do eksploracji nowych elementów treningowych.

#### (Poses List View)[https://github.com/Hortensjaa/Pole_Planner/tree/master/app/src/main/java/com/example/poleplanner/poses_list_view]

`poses_list_view` zawiera listę wszystkich dostępnych figur w aplikacji. 
Użytkownik Z tego widoku użytkownik może dodawać figury do polubionych oraz wchodzić do ich 
podglądu (Detail view). Ponadto zaimplementowana jest (zakładka pozwalająca na filtrowanie)[https://github.com/Hortensjaa/Pole_Planner/tree/master/app/src/main/java/com/example/poleplanner/poses_list_view/composables/filters_sheet] 
oraz (pasek wyszukiwania)[https://github.com/Hortensjaa/Pole_Planner/blob/master/app/src/main/java/com/example/poleplanner/poses_list_view/composables/SearchBar.kt]

## Testy

Projekt zawiera rozbudowany zestaw testów, co pozwala na zapewnienie solidnej jakości kodu i działania aplikacji.

### Testy Jednostkowe

Testy jednostkowe znajdują się w dwóch głównych podfolderach i są uruchamiane za pomocą AndroidJUnit4:

#### (Testy database-accessor-objects)[https://github.com/Hortensjaa/Pole_Planner/tree/master/app/src/androidTest/java/com/example/poleplanner/daoTests] 

Folder `daoTests` zawiera testy jednostkowe dla operacji bazodanowych. 
Testy te sprawdzają poprawność działania interfejsów komunikacji z bazą danych (DAOs), zapewniając integralność przechowywanych danych.

#### (Testy ViewModeli)[https://github.com/Hortensjaa/Pole_Planner/tree/master/app/src/androidTest/java/com/example/poleplanner/viewmodelsTests]

`viewmodelsTests` zawiera testy jednostkowe dla poszczególnych `ViewModels`. 
Dane są mockowane w celu sprawdzenia poprawności logiki biznesowej oraz interakcji między warstwą prezentacji a warstwą danych.


## Instrukcje Instalacji

Aby zainstalować aplikację:

1. Sklonuj repozytorium: `git clone https://github.com/Hortensjaa/Pole_Planner`
2. Otwórz projekt w Android Studio.
3. Skompiluj i uruchom aplikację na emulatorze lub fizycznym urządzeniu.
