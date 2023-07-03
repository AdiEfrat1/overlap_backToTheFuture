const YOUNGSTERS = {
  tableAttributes: ['מספר צעיר', 'שם הצעיר', 'מיקום מגורים', 'טלפון'],
  list: [
    {
      'מספר צעיר': 1,
      'שם הצעיר': 'חבר פרבר',
      'מיקום מגורים': 'פני חבר',
      'טלפון': '058-5675444',
      'תחביב': 'משחקי מחשב',
      'ספר': 'הארי פוטר',
    },
    {
      'מספר צעיר': 46,
      'שם הצעיר': 'עדי שטיינר',
      'מיקום מגורים': 'להבים',
      'טלפון': '051-1234567',
      'תחביב': 'בילויים',
      'ספר': 'חדווא 2',
    },
    {
      'מספר צעיר': 99,
      'שם הצעיר': 'סאני סימן-טוב',
      'מיקום מגורים': 'חולון',
      'טלפון': '012-1234567',
      'תחביב': 'טניס',
      'ספר': 'תלמוד בבלי',
    },
  ],
};

const YOUNG_ID = 'מספר צעיר';
const YOUNG_NAME = 'שם הצעיר';
const ACTIVE_MULTI_SELECT_CLASS = 'active-multi-select';
const ACTIVE_SIDE_BTN_CLASS = 'active-side-btn';
const CHOSEN_YOUNG_CLASS = 'chosen-young';

let chosenYoungList = [];
let realTimeClock = false;

document.addEventListener('DOMContentLoaded', () => {
  updateLastLogin(currTime());
  updateClock(currTime());

  const clockBtn = document.getElementById('clock-btn');
  clockBtn.addEventListener("click", toggleRealTimeClock);

  const sideBtns = document.getElementsByClassName('side-bar-btn');
  Array.from(sideBtns).forEach((sideBtn) => sideBtn.addEventListener("click", pressedSideBtn));
});

const currTime = () => {
  const date = new Date();
  
  return date.toLocaleTimeString('it-IT');
};

const updateClock = (time) => {
  const clock = document.getElementById('clock-time');
  clock.innerText = time;
};

const updateLastLogin = (time) => {
  const timeWithoutSecs = time.slice(0, 5);
  
  const lastLogin = document.getElementById('last-login');
  lastLogin.appendChild(document.createTextNode(' ' + timeWithoutSecs));
};

const toggleRealTimeClock = (event) => {
  const clockBtn = event.target;

  if (realTimeClock) {
    clearInterval(clockInteval);
    clockBtn.innerText = 'הפעל';
  } else {
    clockInteval = setInterval('updateClock(currTime())', 1000);
    clockBtn.innerText = 'הפסק';
  }

  realTimeClock = !realTimeClock;
};

const clearDetails = () => {
  const detailsSection = document.getElementById('details-section');
  detailsSection.innerHTML = '';
};

const generateDetailsContainers = () => {
  const detailsSection = document.getElementById('details-section');

  const allDetails = document.createElement('div');
  allDetails.setAttribute('id', 'all-details');
  allDetails.classList.add('bordered');

  const allDetailsContent = document.createElement('div');
  allDetailsContent.setAttribute('id', 'all-details-content');

  allDetails.appendChild(document.createTextNode('כל הפרטים:'));
  allDetails.appendChild(allDetailsContent);

  const specificDetails = document.createElement('div');
  specificDetails.setAttribute('id', 'specific-details');
  specificDetails.classList.add('bordered');

  const specificDetailsContent = document.createElement('div');
  specificDetailsContent.setAttribute('id', 'specific-details-content');

  specificDetails.appendChild(document.createTextNode('פרטים ספציפים:'));
  specificDetails.appendChild(specificDetailsContent);

  detailsSection.appendChild(allDetails);
  detailsSection.appendChild(specificDetails);
};

const pressedSideBtn = (event) => {
  setActiveSideBtn(event);
  clearDetails();
  chosenYoungList = [];

  const pressedBtn = event.target;
  const pressedBtnID = pressedBtn.getAttribute('id');

  if (pressedBtnID === 'young-btn') {
    generateYoungDetails();
  } else {
    const tabName = pressedBtnID.substring(0, pressedBtnID.indexOf("-"));
    generateTabImage(tabName);
  }
};

const setActiveSideBtn = (event) => {
  const activeBtns = document.getElementsByClassName(ACTIVE_SIDE_BTN_CLASS);

  Array.from(activeBtns).forEach((activeBtn) => {
    activeBtn.classList.remove(ACTIVE_SIDE_BTN_CLASS);
  });

  const pressedBtn = event.target;
  pressedBtn.classList.add(ACTIVE_SIDE_BTN_CLASS);
};

const generateTabImage = (tabName) => {
  const detailsSection = document.getElementById('details-section');

  const tabImg = document.createElement('img');
  tabImg.setAttribute('src', `./assets/${tabName}.png`)
  tabImg.setAttribute('alt', tabName);
  tabImg.classList.add('tab-img');

  detailsSection.appendChild(tabImg);
};

const generateYoungDetails = () => {
  generateDetailsContainers();

  const content = document.getElementById('all-details-content');

  const multiSelectBtn = document.createElement('button');
  multiSelectBtn.setAttribute('id', 'multi-selection-btn');
  multiSelectBtn.appendChild(document.createTextNode('+'));
  multiSelectBtn.addEventListener('click', toggleMultiSelect);

  content.appendChild(multiSelectBtn);

  const tbl = document.createElement('table');
  tbl.setAttribute('id', 'young-table');

  const thead = document.createElement('thead');
  thead.setAttribute('id', 'young-table-head');

  const headerRow = document.createElement('tr');

  YOUNGSTERS.tableAttributes.forEach((attribute) => {
    const header = document.createElement('th');

    const headerContent = document.createElement('div');
    headerContent.classList.add('young-header-content');

    const sortBtnsContainer = document.createElement('div');
    sortBtnsContainer.classList.add('sort-btns-container');

    const sortUpBtn = document.createElement('div');
    sortUpBtn.classList.add('young-sort-btn', 'sort-up');
    sortUpBtn.setAttribute('sort-property', attribute);
    sortUpBtn.addEventListener('click', displaySortedYoungRows);

    const sortDownBtn = document.createElement('div');
    sortDownBtn.classList.add('young-sort-btn', 'sort-down');
    sortDownBtn.setAttribute('sort-property', attribute);
    sortDownBtn.addEventListener('click', displaySortedYoungRows);

    sortBtnsContainer.appendChild(sortUpBtn);
    sortBtnsContainer.appendChild(sortDownBtn);

    headerContent.appendChild(document.createTextNode(attribute));
    headerContent.appendChild(sortBtnsContainer);

    header.appendChild(headerContent);

    headerRow.appendChild(header);
  });

  thead.appendChild(headerRow);
  tbl.appendChild(thead);

  const tbody = document.createElement('tbody');
  tbody.setAttribute('id', 'young-table-body');

  YOUNGSTERS.list.forEach((youngster) => {
    const tr = document.createElement('tr');

    Object.keys(youngster).forEach((key) => {
      if (YOUNGSTERS.tableAttributes.includes(key)) {
        const td = document.createElement('td');
        td.appendChild(document.createTextNode(youngster[key]));
        td.addEventListener("click", generateYoungSpecific);

        tr.appendChild(td);
      }
    });

    tbody.appendChild(tr);
  });

  tbl.appendChild(tbody);
  content.appendChild(tbl);
};

const generateYoungSpecific = (event) => {
  const chosenCell = event.target;
  const multiSelectBtn = document.getElementById('multi-selection-btn');

  if (!multiSelectBtn.classList.contains(ACTIVE_MULTI_SELECT_CLASS)) {
    removeAllOfClass(CHOSEN_YOUNG_CLASS);
    chosenYoungList = [];
  }

  const chosenRow = chosenCell.parentElement;
  const chosenID = parseInt(chosenRow.childNodes[0].innerText);
  const index = chosenYoungList.indexOf(chosenID);

  if (index !== -1) {
    chosenYoungList.splice(index, 1);
  }

  chosenRow.classList.add(CHOSEN_YOUNG_CLASS);
  chosenYoungList.unshift(chosenID);
  updateYoungSpecific();
};

const removeAllOfClass = (className) => {
  const classElements = document.getElementsByClassName(className);

  Array.from(classElements).forEach((currElement) => currElement.classList.remove(className));
};

const removeChosenYoungButLast = () => {
  const chosenYoungs = document.getElementsByClassName(CHOSEN_YOUNG_CLASS);

  Array.from(chosenYoungs).forEach((currChosen) => {
    const currID = parseInt(currChosen.childNodes[0].innerText);

    if (chosenYoungList[0] !== currID) {
      currChosen.classList.remove(CHOSEN_YOUNG_CLASS);
    }
  });

  chosenYoungList = [chosenYoungList[0]];
};

const updateYoungSpecific = () => {
  const content = document.getElementById('specific-details-content');
  content.innerText = '';

  chosenYoungList.forEach((chosenRowId) => {
    const chosenYoung = YOUNGSTERS.list.find((youngster) => youngster[YOUNG_ID] === chosenRowId);

    const chosenDetails = document.createElement('div');
    chosenDetails.classList.add('young-details-line');

    Object.keys(chosenYoung).forEach((attribute) => {
      if (!YOUNGSTERS.tableAttributes.includes(attribute)) {
        const attrText = document.createElement('div');
        attrText.appendChild(document.createTextNode(`${attribute}: ${chosenYoung[attribute]}`));
        chosenDetails.appendChild(attrText);
      }
    });

    const nameText = document.createElement('div');
    nameText.appendChild(document.createTextNode(`שם: ${chosenYoung[YOUNG_NAME]}`));
    chosenDetails.appendChild(nameText);

    content.appendChild(chosenDetails);
  });
};

const toggleMultiSelect = (event) => {
  const multiSelectBtn = event.target;

  if (multiSelectBtn.classList.contains(ACTIVE_MULTI_SELECT_CLASS)) {
    removeChosenYoungButLast();
    updateYoungSpecific();

    multiSelectBtn.classList.remove(ACTIVE_MULTI_SELECT_CLASS);
  } else {
    multiSelectBtn.classList.add(ACTIVE_MULTI_SELECT_CLASS);
  }
};

const sortYoungRows = (event) => {
  const sortBtn = event.target;
  const sortProperty = sortBtn.getAttribute('sort-property');

  const sortedYoungs = YOUNGSTERS.list.sort((a, b) => {
    propA = a[sortProperty].toString();
    propB = b[sortProperty].toString();

    return sortBtn.classList.contains('sort-up') ? propA.localeCompare(propB) : propB.localeCompare(propA);
  });

  return sortedYoungs;
};

const setYoungSortBtnActive = (event) => {
  const ACTIVE_SORT = 'active-sort';

  const activeSortBtns = document.getElementsByClassName(ACTIVE_SORT);

  if (activeSortBtns.length) {
    const activeBtn = activeSortBtns[0];
    activeBtn.classList.remove(ACTIVE_SORT);
  }

  const pressedActive = event.target;
  pressedActive.classList.add(ACTIVE_SORT);
};

const displaySortedYoungRows = (event) => {
  const sortedYoungs = sortYoungRows(event);

  const tbody = document.getElementById('young-table-body');
  tbody.innerHTML = '';

  sortedYoungs.forEach((youngster) => {
    const tr = document.createElement('tr');

    if (chosenYoungList.includes(youngster[YOUNG_ID])) {
      tr.classList.add('chosen-young');
    }

    Object.keys(youngster).forEach((key) => {
      if (YOUNGSTERS.tableAttributes.includes(key)) {
        const td = document.createElement('td');
        td.appendChild(document.createTextNode(youngster[key]));
        td.addEventListener("click", generateYoungSpecific);

        tr.appendChild(td);
      }
    });

    tbody.appendChild(tr);
  });

  setYoungSortBtnActive(event);
};