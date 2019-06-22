const fs = require("fs");
const path = require("path");
const request = require("request");

const URLS = {
  checkstyle:
    "https://github.com/checkstyle/checkstyle/releases/download/checkstyle-8.21/checkstyle-8.21-all.jar",
  googleStyle:
    "https://raw.githubusercontent.com/checkstyle/checkstyle/master/src/main/resources/google_checks.xml"
};

const ROOT_PATH = path.join(__dirname, "..");
const OUT_PATH = path.join(ROOT_PATH, "checkstyle");
const BACKEND_PATH = path.join(ROOT_PATH, "Backend");

const FILE_DEFAULTS = {
  "Backend/src/main/resources/config.json": JSON.stringify(
    {
      mysql: {
        host: "localhost:3306",
        username: "root",
        password: "",
        database: "betteroscar"
      }
    },
    null,
    2
  )
};

function downloadFile(url, dest) {
  return new Promise((resolve, reject) => {
    request(url)
      .on("error", reject)
      .pipe(fs.createWriteStream(dest))
      .on("close", resolve);
  });
}

function getNameFromUrl(url) {
  const spl = url.split("/");
  return spl[spl.length - 1];
}

async function downloadPart(url, name) {
  const fileName = getNameFromUrl(url);
  const filePath = path.join(OUT_PATH, fileName);
  if (fs.existsSync(filePath)) {
    console.log(`${name} found, skipping`);
  } else {
    console.log(`Downloading ${name}...`);
    await downloadFile(url, filePath);
  }
  return filePath;
}

function initGitignoredFiles() {
  Object.keys(FILE_DEFAULTS).forEach(file => {
    const filePath = path.join(ROOT_PATH, file);
    if (!fs.existsSync(filePath)) {
      console.log(`Creating ${file}`);
      fs.writeFileSync(filePath, FILE_DEFAULTS[file]);
    }
  });
}

async function main() {
  if (!fs.existsSync(OUT_PATH)) {
    fs.mkdirSync(OUT_PATH);
  }

  const checkstyleFile = await downloadPart(URLS.checkstyle, "Checkstyle");

  const styleFile = await downloadPart(URLS.googleStyle, "Google Style");

  const checkstyleCommand = `java -jar "${checkstyleFile}" -c="${styleFile}" "${path.join(
    BACKEND_PATH,
    "src"
  )}"`;

  const commandFileName =
    process.platform === "win32" ? "checkstyle.bat" : "checkstyle.sh";

  console.log(`Creating Backend/${commandFileName}`);
  fs.writeFileSync(path.join(BACKEND_PATH, commandFileName), checkstyleCommand);

  console.log("Checking gitignored files");
  initGitignoredFiles();

  console.log("Finished");
}

main().catch(console.error);
