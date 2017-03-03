<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="user-details">
    <div class="form-group row">
        <label class="col-xs-2" for="email-input">email-адрес</label>
        <div class="col-xs-10">
            <input type="text" class="form-control" id="email-input" name="email" placeholder="email-адрес">
        </div>
    </div>
    <div class="form-group row">
        <label class="col-xs-2" for="password-input">Пароль</label>
        <div class="col-xs-10">
            <input type="password" class="form-control" id="password-input" name="password" placeholder="Пароль">
        </div>
    </div>
    <hr>
    <div class="form-group row">
        <label class="col-xs-2" for="firstname-input">Имя</label>
        <div class="col-xs-10">
            <input type="text" class="form-control" id="firstname-input" name="firstname" placeholder="Введите имя">
        </div>
    </div>
    <div class="form-group row">
        <label class="col-xs-2" for="surname-input">Фамилия</label>
        <div class="col-xs-10">
            <input type="text" class="form-control" id="surname-input" name="surname" placeholder="Введите фамилию">
        </div>
    </div>
    <div class="form-group row">
        <label class="col-xs-2" for="patronymic-input">Отчество</label>
        <div class="col-xs-10">
            <input type="text" class="form-control" id="patronymic-input" name="patronymic" placeholder="Введите отчество">
        </div>
    </div>
    <div class="form-group row">
        <label class="col-xs-2" for="birthday-input">Дата рождения</label>
        <div class="col-xs-10">
            <input type="date" class="form-control" id="birthday-input" name="birthday" required>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-xs-2" for="sex-input">Пол</label>
        <div class="col-xs-10">
            <select class="form-control" id="sex-input" name="sex">
                <option value="M">Мужской</option>
                <option value="F">Женский</option>
            </select>
        </div>
    </div>
</div>