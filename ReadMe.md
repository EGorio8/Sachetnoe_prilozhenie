Электронная книжка волонтера
-
Приложение “Электронная книжка волонтера” было создано для облегчения организационной работы волонтеров и организаторов. Каждый пользователь в состоянии быть как просто волонтером, так и организатором.


Мотивация
-
Проект был сделан нашей командой для информатизации электронной книжки волонтера. Нашей целью являлось облегчение взаимодействия волонтеров с организаторами посредством автоматизации процессов, связанных с мероприятиями.


Статус сборки
-
Стабильная сборка проекта.


Стиль кода
-
Код проекта написан в стандартном стиле кода.


Используемая технология/фреймворк
-
Проект разрабатывался на языке java; в процессе разработки были использованы следующие технологии:


Функции
-
Среди функционала для волонтеров стоит отметить интуитивный интерфейс выбора мероприятия для участия и систему рейтинга. За высокий рейтинг волонтер получает возможность приобрести мерч волонтерской организации за эти баллы.
Для организаторов предоставлен функционал создания, модерации и завершения мероприятий с раздачей каждому волонтеру баллы.

Установка
-
Приложение не требует установки дополнительного программного обеспечения.

Справочник по API
-
*документация по API*

Тесты
-
В этом разделе вы упоминаете все различные тесты, которые можно выполнить с примерами кода.

Как использовать?
-
Авторизация(Регистрация):
-
Кнопка “Войти”: вход в систему пользователем, соответствующим введенным логином и паролем.

Кнопка “Зарегистрироваться”: трансформирует экран на экран регистрации. На этом экране кнопка записывает данные введенные в поля пароля и логина, а также ФИО и пол,  как нового пользователя в БД и производит вход за этого пользователя. Новому пользователю присваивается нулевой ранг (медь).

Поле для ввода логина: на экране авторизации введенный логин, при нажатии “Войти”, будет найден в БД. На экране регистрации введенный логин, если не существовал ранее, будет записан в БД.

Поле для ввода пароля: на экране авторизации введенный пароль, при нажатии кнопки “Войти”, будет сверен с паролем пользователя с указанным логином. На экране регистрации введенный пароль, при нажатии кнопки “Зарегистрироваться”, будет использован как пароль этого пользователя.

Главное окно (Главное меню):
-
Кнопка “Личный кабинет”: перенаправляет пользователя на экран с личным кабинетом.

Кнопка “Управлять”: перенаправляет пользователя на экран “Окно организации(организатора) мероприятий”.

Поле доступных мероприятий: здесь указываются названия и описание всех мероприятий, к которым можно присоединиться волонтеру. Каждое мероприятие, при нажатии на него, перенаправляет пользователя на экран этого мероприятия.
Окно Регистрации события(Мероприятия):

Кнопка “Создать мероприятие”: создает мероприятие с указанными параметрами (Название мероприятия, ФИО организатора, Дата окончания, Описание мероприятия).

Кнопка “Вернуться”: возвращает пользователя на главный экран.

Поле “Название мероприятия”: при нажатии кнопки “создать мероприятие”, сохраняет указанное в поле как название мероприятия.

Поле “ФИО организатора”: при нажатии кнопки “создать мероприятие”, сохраняет указанное в поле как ФИО организатора.

Поле “Дата”: при нажатии кнопки “создать мероприятие”, сохраняет указанное в поле как дата завершения события.

Поле “Описание мероприятия”: при нажатии кнопки “создать мероприятие”, сохраняет указанное в поле как описание мероприятия.

Окно личного кабинета:
-
Кнопка “Редактировать данные пользователя”: перенаправляет пользователя на экран редактирования данных пользователя.

Кнопка “Мои события”: перенаправляет пользователя на экран “Мои события”.

Кнопка “Мои мероприятия”: перенаправляет пользователя на экран “Мои мероприятия”

Кнопка “Вернуться”: возвращает пользователя на главный экран.

Поле “Мой рейтинг”: здесь указывается количество очков волонтера. Также здесь указывается медаль статуса волонтера и сам статус, складывающееся из количества очков волонтера:
0 до 200 – медный
201 до 400 – бронзовый
401-600 – серебряный
601-800 – золотой
801 – 1000 – бриллиантовый

Окно организации(организатора) мероприятий:
-
Кнопка “Вернуться”: возвращает пользователя на главный экран.

Поле активных организованных мероприятий: здесь указываются все активные мероприятия, созданные пользователем. У каждого мероприятия есть кнопка “Управлять”, которая переносит на экран организованного мероприятия.

Кнопка “Добавить”: переносит пользователя на экран регистрации события.

Окно организованного мероприятия:
-
Поле “Участники”: здесь указываются все принятые волонтеры.

Поле “Заявки”: здесь указываются все волонтеры, подавшие заявку на участие в мероприятии.

Поле “Количество баллов”: здесь можно указать количество баллов, которое раздастся участникам при нажатии кнопки “Раздать баллы”.

Кнопки “Баллы x2”: кнопка удваивает указанное в поле “Количество баллов” при нажатии кнопки “Раздать баллы”.

Кнопка “Вернуться”: возвращает пользователя на экран организации(организатора) мероприятий.

Кнопки “Принять”: кнопка добавляет волонтера в список участников.

Кнопка “Раздать баллы”: раздает участникам указанное в поле “Количество баллов” количество баллов и завершает мероприятие.

Кнопка “Отменить мероприятие”: удаляет мероприятие.

Окно Мои события:
-
Поле оповещений: здесь указываются все оповещения о принятии заявок волонтера, появления заявок на участие в организованных мероприятиях и начисления (или убывания) баллов.

Кнопка “Вернуться в личный кабинет”: возвращает пользователя на экран личного кабинета.

Окно Мои мероприятия:
-
Поле “Заявки”: тут указаны все мероприятия на которые волонтер подал заявки.

Поле “Активные мероприятия”: тут указываются все мероприятия в которых приняли волонтера.

Поле “Завершенные мероприятия”: тут указываются все завешенные волонтером мероприятия, а также количество полученных баллов.

Кнопка “Вернуться в личный кабинет”: возвращает пользователя на экран личного кабинета.

Перспективы
-
Для привлечения новых пользователей можно было бы увеличить наполнение интерфейса и количества выполняемых приложением функций.

Контактная информация
-
Контакты разработчика:

Таранец Е.В. - 8 (977) 164 23-84

Старшинов В.Ю. - 8 (495) 135 75-95

Герасимов Г.А. - 8 (800) 555 35-35

Контакты техподдержки:

Таранец Е.В. - 8 (977) 164 23-84

Старшинов В.Ю. - 8 (495) 135 75-95

Герасимов Г.А. - 8 (800) 555 35-35