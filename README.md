<section class="lesson proficiency-page__content">
<section class="theory-viewer prisma prisma_theme_light big-theory lesson__theory">
<section class="theory-viewer__blocking-layout-block theory-viewer__block theory-viewer__block_type_vertical-layout theory-viewer__block_layout">
<section class="theory-viewer__block theory-viewer__block_type_markdown">
<div class="Markdown base-markdown base-markdown_with-gallery markdown markdown_size_normal markdown_type_theory full-markdown">
<h1>Задание 2: API</h1>
</div>
</section>
<section class="theory-viewer__block theory-viewer__block_type_markdown">
<div class="Markdown base-markdown base-markdown_with-gallery markdown markdown_size_normal markdown_type_theory full-markdown">
<div class="paragraph">Тебе нужно протестировать ручки API для <a href="https://stellarburgers.nomoreparties.site/" target="_blank">Stellar Burgers</a>.</div>
<div class="paragraph">Пригодится <a href="https://code.s3.yandex.net/qa-automation-engineer/java/cheatsheets/paid-track/diplom/api-documentation.pdf" 
target="_blank">документация API</a>. В ней описаны все ручки сервиса. Тестировать нужно только те, которые указаны в задании. Всё остальное — просто для контекста.</div>
<div class="paragraph">
<strong>Создание пользователя:</strong>
</div>
<ul>
<li>создать уникального пользователя;</li>
<li>создать пользователя, который уже зарегистрирован;</li>
<li>создать пользователя и не заполнить одно из обязательных полей.</li>
</ul>
<div class="paragraph">
<strong>Логин пользователя:</strong>
</div>
<ul>
<li>логин под существующим пользователем,</li>
<li>логин с неверным логином и паролем.</li>
</ul>
<div class="paragraph">
<strong>Изменение данных пользователя:</strong>
</div>
<ul>
<li>с авторизацией,</li>
<li>без авторизации,</li>
</ul>
<div class="paragraph">Для обеих ситуаций нужно проверить, что любое поле можно изменить. Для неавторизованного пользователя — ещё и то, что система вернёт ошибку.</div>
<div class="paragraph">
<strong>Создание заказа:</strong>
</div>
<ul>
<li>с авторизацией,</li>
<li>без авторизации,</li>
<li>с ингредиентами,</li>
<li>без ингредиентов,</li>
<li>с неверным хешем ингредиентов.</li>
</ul>
<div class="paragraph">
<strong>Получение заказов конкретного пользователя:</strong>
</div>
<ul>
<li>авторизованный пользователь,</li>
<li>неавторизованный пользователь.</li>
</ul>
<h3>Что нужно сделать</h3>
<div class="paragraph">Создай отдельный репозиторий для тестов API.</div>
<div class="paragraph">Создай Maven-проект.</div>
<div class="paragraph">Подключи JUnit 4, RestAssured и Allure.</div>
<div class="paragraph">Напиши тесты.</div>
<div class="paragraph">Сделай отчёт в Allure.</div>
<h3>Как выполнить и сдать работу</h3>
<div class="paragraph">Создай проект в IntelliJ IDEA, залей его на GitHub, запушь ветку <code class="code-inline code-inline_theme_light">develop2</code> и сделай пул-реквест.
<a href="https://code.s3.yandex.net/qa-automation-engineer/java/cheatsheets/paid-track/diplom/upload-tasks-2-and-3.pdf" target="_blank">Подробная инструкция</a>.</div>
<h3>Как будут оценивать твою работу</h3>
<ol start="1">
<li>Для каждой ручки тесты лежат в отдельном классе.</li>
<li>Тесты запускаются и проходят.</li>
<li>Написаны все тесты, указанные в задании.</li>
<li>В тестах проверяется тело и код ответа.</li>
<li>Все тесты независимы.</li>
<li>Нужные тестовые данные создаются перед тестом и удаляются после того, как он выполнится.</li>
<li>Сделан Allure-отчёт. Отчёт добавлен в пул-реквест.</li>
<li>Тесты в <code class="code-inline code-inline_theme_light">test/java</code>.</li>
<li>В файле <code class="code-inline code-inline_theme_light">pom.xml</code> нет ничего лишнего.</li>
<li>В проекте используется Java 11.</li>
</ol>
</div>
</section>
</section>
</section>
<div class="lesson__markdown-btn lesson__markdown-btn_with-progress">
<div class="next-lesson-control next-lesson-control_state_ready prisma prisma_theme_light lesson__next-lesson-control">
</div>
</div>
</section>