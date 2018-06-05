<!DOCTYPE html>
<%@page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/webjars/bootstrap/3.3.7/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/webjars/datatables/1.10.16/css/dataTables.bootstrap.min.css"/>

    <script src="/webjars/jquery/3.1.1/jquery.js"></script>
    <script src="/webjars/datatables/1.10.16/js/jquery.dataTables.min.js"></script>
    <script src="/webjars/datatables/1.10.16/js/dataTables.bootstrap.min.js"></script>

    <style type="text/css">
        .dataTables_filter {
            width: 90%;
        }

        .toolbar {
            float: left;
        }
    </style>
</head>
<body>
    <div id="alert" class="alert alert-info alert-dismissible">
        <span>Тестовая версия приложения для работы с почтой</span>
    </div>

    <span style="font-weight:20">Темы писем</span>

    <button id="topicAdd" class="btn-default glyphicon glyphicon-plus" tooltip="Добавить"></button>

    <table id="topicsTable" class="table table-bordered" style="width: 50%;">
        <thead>
            <tr>
                <th class="col-md-1">Идентификатор</th>
                <th class="col-md-5">Наименование</th>
                <th class="col-md-1">Действия</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${topics}" var="topic">
                <tr>
                    <td>${topic.id}</td>
                    <td editable>${topic.name}</td>
                    <td>
                        <button class="btn-default glyphicon glyphicon-pencil" tooltip="Редактировать"></button>
                        <button class="btn-default glyphicon glyphicon-minus" tooltip="Удалить"></button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <br/>

    <table id="emailsTable" class="table table-bordered" style="width: 90%;">
        <thead>
            <tr>
                <th>Тема письма</th>
                <th>Отделение банка</th>
                <th>Категория получателей</th>
                <th align="left">Адрес электронной почты</th>
                <th>Действия</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${emails}" var="emailInfo">
                <tr>
                    <td align="center" data-id="${emailInfo.topic.id}">${emailInfo.topic.name}</td>
                    <td align="center" data-id="${emailInfo.branch.id}">${emailInfo.branch.name}</td>
                    <td align="center" data-id="${emailInfo.category.id}">${emailInfo.category.name}</td>
                    <td style="max-width: 600px; word-wrap: break-word;" editable>
                        <c:forEach var="email" items="${fn:split(emailInfo.emailAddress, ',')}">
                            <span style="margin-left: 5px;">${email}</span>
                        </c:forEach>
                    </td>
                    <td>
                        <button class="btn-default glyphicon glyphicon-pencil" tooltip="Редактировать"></button>
                        <button class="btn-default glyphicon glyphicon-minus" tooltip="Удалить"></button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
<script>

    var emailCaption = "<span style=\"font-weight:20;\">Получатели писем</span>";
    var emailAdd = "<button id=\"emailAdd\" class=\"btn-default glyphicon glyphicon-plus\" tooltip=\"Добавить\"></button>";

    var yesButton = "<button class = \"btn-success glyphicon glyphicon-ok\"/>";
    var noButton = "<button class = \"btn-danger glyphicon glyphicon-remove\"/>";

    var addButton = "<button class=\"btn-default glyphicon glyphicon-plus\"/>";

    var emailInput = "<input style=\"width:100%; margin-top:5px;\"/>";

    function getSelect(objectsType, hasEmpty) {
        var select = $("<select/>")

        if (hasEmpty) {
            $(select).append("<option/>");
        }

        $.getJSON("email/get" + objectsType, function(data) {
            $.each(data, function (index, value) {
                $("<option/>", {value: "" + this.id + "", text: this.name}).appendTo(select);
            });
        });

        return select;
    }

    function alertError(errorMessage) {
        $("#alert").removeClass("alert-info").addClass("alert alert-danger");
        $("#alert").find("span").text(errorMessage);

        $('html, body').animate({scrollTop: 0});
    }

    $(document).on('click', '.glyphicon-plus', function() {
        if ($(this).data("action") == 'addEmailAddress') {
            $(emailInput).insertBefore($(this));
        } else {
            var newCellActions = $("<td></td>")
                .append($(yesButton).data("action", "add"))
                .append($(noButton).data("action", "add"));

            var newRow = $("<tr></tr>");

            var tableId;
            if ($(this).prop("id").indexOf('topic') >= 0) {
                tableId = "topicsTable";

                for (var i = 0; i < $('#' + tableId).find("thead th").length - 1; i ++) {
                    $(newRow).append("<td><input/></td>");
                }
            } else if ($(this).prop("id").indexOf('email') >= 0) {
                tableId = "emailsTable";

                $(newRow).append($("<td/>").append(getSelect('Topics', false)));
                $(newRow).append($("<td/>").append(getSelect('Branches', true)));
                $(newRow).append($("<td/>").append(getSelect('Categories', false)));

                var emailCell = $("<td/>").append($(emailInput)).append($(addButton).data("action", "addEmailAddress"));
                $(newRow).append(emailCell);
            }

            $(newRow).append(newCellActions);

            $('#' + tableId).find("tbody").append(newRow);

            if (tableId == "emailsTable") {
                $('html, body').animate({scrollTop: $(newRow).offset().top}, 1000);
            }
        }
    });

    $(document).on('click', '.glyphicon-pencil', function() {
        var tableId = $(this).parents("table").prop("id");

        var row = $(this).parents("tr");

        var editableCell = $(row).find("td[editable]");
        var cellClone = $(editableCell).clone();

        var cellValue = $(editableCell).html();

        $(editableCell).empty();

        if (tableId.indexOf("topics") >= 0) {
            $(editableCell).append($("<input/>").prop("style", "width:100%").val(cellValue));
        } else if (tableId.indexOf("emails") >= 0) {
            $(cellClone).find("span").each(function(index, span) {
                $(editableCell).append($(emailInput).val($(span).text()));
            });
            $(editableCell).append($(addButton).data("action", "addEmailAddress"));
        }

        $(row).find("td:last").empty();
        $(row).find("td:last")
            .append($(yesButton).data("action", "edit"))
            .append($(noButton).data("action", "edit"));
    });

    $(document).on('click', '.glyphicon-minus', function() {
        var url, params;

        var row = $(this).parents("tr");

        $(row).find("td:last").empty();
        $(row).find("td:last")
            .append($(yesButton).data("action", "delete"))
            .append($(noButton).data("action", "delete"));
    });

    $(document).on('click', '.btn-success', function() {
        var url, params;

        var tableId = $(this).parents("table").prop("id");
        var row = $(this).parents("tr");
        if (tableId.indexOf("topics") >= 0) {
            if ($.inArray($(this).data("action"), ['add', 'edit']) >= 0) {
                var topicId;
                if ($(this).data("action") == 'add') {
                    topicId = $(row).find("td:nth-child(1) > input").val();
                } else {
                    topicId = $(row).find("td:first").html();
                }

                if (topicId.length == 0) {
                    alertError('Идентификатор темы не может быть пустой');
                    return;
                }

                var topicName = $(row).find("td:nth-child(2) > input").val();

                url = "setTopic";
                params = {"id" : topicId, "name" : topicName};
            } else if ($(this).data("action") == 'delete') {
                var topicId = $(row).find("td:first").html();

                url = "deleteTopic";
                params = {"id" : topicId};
            }
        } else if (tableId.indexOf("emails") >= 0) {
            if ($.inArray($(this).data("action"), ['add', 'edit']) >= 0) {
                var topicId, branchId, categoryId;
                if ($(this).data("action") == 'add') {
                    topicId = $(row).find("td:nth-child(1) > select > option:selected").val();
                    branchId = $(row).find("td:nth-child(2) > select > option:selected").val();
                    categoryId = $(row).find("td:nth-child(3) > select > option:selected").val();
                } else {
                    topicId = $(row).find("td:first").attr("data-id");
                    branchId = $(row).find("td:nth-child(2)").attr("data-id");
                    categoryId = $(row).find("td:nth-child(3)").attr("data-id");
                }

                var emails = [];
                $(row).find("td:nth-child(4) > input").each(function(index, input) {
                    var email = $(input).val();
                    if (email.length > 0) {
                         emails = emails.concat(email);
                    }
                });

                url = 'setEmail';
                params = {"topicId" : topicId, "branchId" : branchId.length == 0 ? -1 : branchId,
                        "categoryId" : categoryId, "email" : emails.join()};
            } else if ($(this).data("action") == 'delete') {
                var topicId = $(row).find("td:first").attr("data-id");
                var branchId = $(row).find("td:nth-child(2)").attr("data-id");
                var categoryId = $(row).find("td:nth-child(3)").attr("data-id");

                url = 'deleteEmail';
                params = {"topicId" : topicId, "branchId" : branchId.length == 0 ? -1 : branchId, "categoryId" : categoryId};
            }
        }

        $.post({
            url: "email/actions/" + url,
            data: params,
            success: function(response) {
                if (response) {
                    if (response.success) {
                        location.reload();
                    } else {
                        alertError(response.errorMessage);
                    }
                } else {
                    location.reload();
                }
            },
            dataType : "json",
         })
    });

    $(document).on('click', '.btn-danger', function() {
        location.reload();
    });

    $(document).ready(function() {
        $('#emailsTable').DataTable({
            "paging":   false,
            "order":    [],
            "info":     false,
            "dom":      '<"toolbar">frtip',
            "language":
            {
                "zeroRecords":  "Данные не найдены",
                "search":       "Поиск:"
            },
            "columnDefs":
            [
                {
                    "targets": 3,
                    "orderable": false
                },
                {
                    "targets": 4,
                    "orderable": false
                }
            ]
        });

        $("div.toolbar").append(emailCaption).append(emailAdd);
    });

</script>
</html>