# google-spreadsheet-importer

A console application that could import CSV into Google Spreadsheet.
This app could be used for automate fill daily a spreadsheet from resultset of sql executeb by cron for using like a DataSudio source.

# Setup project

## Step 1 - Setup Google Platform Project

1.1 Create a project:

    https://console.cloud.google.com/apis/dashboard

1.2 Enable the Spreadsheet and Drive APIs for this project.
1.3 Create a Credential of type "Service Account Key" for OFFLINE console application.
1.4 Download JSON Credential for using with google-spreadsheet-importer.

## Step 2 - Configure YML and execute app.



## Usage


1. Create new Sheet ( command, sheetname, headers with ";" separator )

```
java -jar google-spreadsheet-importer-1.0.RELEASE.jar new-sheet teste-google-api004 "Data;Ano;Mês;Dia;Cadastros;Total;Cadastros Ativos;Habilitações Total;Lances Total;Leilões;Lotes Ofertados"
```

2. Import CSV file to existent Sheet ( command, spreadsheetId returned from new-sheet, csvpath )

```
java -jar google-spreadsheet-importer-1.0.RELEASE.jar import-csv 14GUxKBspV0MF_7A4zmkYflX8TqLdbYyk4w9eELEhUKI C:\Users\eduardo\Documents\workspace\google-spreadsheet-importer\accounts-sample.csv
```

3. Append a row to existent sheet ( command, spreadsheetId returned from new-sheet, values with ";" separator )

```
java -jar google-spreadsheet-importer-1.0.RELEASE.jar append-row 14GUxKBspV0MF_7A4zmkYflX8TqLdbYyk4w9eELEhUKI "20180104;2018;jan;4;163;68;453;508;93;4.059"
```
