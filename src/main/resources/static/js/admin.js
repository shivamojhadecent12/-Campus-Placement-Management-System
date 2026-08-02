/* Admin / Placement Officer Dashboard Controller */

document.addEventListener('DOMContentLoaded', async () => {
  AuthManager.requireAuth('ADMIN', '/index.html');
  const user = AuthManager.getCurrentUser();

  document.getElementById('adminNavName').textContent = user.name || 'Placement Officer';

  await loadDashboardStats();
  await loadCompanies();
  await loadPlacementDrives();
  await loadStudents();
  await loadApplications();

  // Event Listeners
  document.getElementById('saveCompanyForm')?.addEventListener('submit', handleSaveCompany);
  document.getElementById('saveDriveForm')?.addEventListener('submit', handleSaveDrive);
  
  document.getElementById('logoutBtn')?.addEventListener('click', (e) => {
    e.preventDefault();
    AuthManager.logout();
  });
});

let allCompaniesList = [];
let allDrivesList = [];

async function loadDashboardStats() {
  try {
    const stats = await ApiClient.get('/admin/stats');
    document.getElementById('statTotalStudents').textContent = stats.totalStudents || 0;
    document.getElementById('statTotalCompanies').textContent = stats.totalCompanies || 0;
    document.getElementById('statTotalDrives').textContent = stats.totalDrives || 0;
    document.getElementById('statTotalPlaced').textContent = `${stats.totalPlacedStudents} (${stats.placementPercentage}%)`;
  } catch (err) {
    console.error("Failed to load admin stats:", err);
  }
}

/* ================= COMPANIES ================= */
async function loadCompanies() {
  const tbody = document.getElementById('companiesTableBody');
  const companySelect = document.getElementById('driveCompanySelect');
  if (!tbody) return;

  try {
    allCompaniesList = await ApiClient.get('/companies');
    tbody.innerHTML = '';
    if (companySelect) companySelect.innerHTML = '<option value="" disabled selected>Select Company</option>';

    if (!allCompaniesList || allCompaniesList.length === 0) {
      tbody.innerHTML = `<tr><td colspan="6" class="text-center text-muted py-3">No companies added yet.</td></tr>`;
      return;
    }

    allCompaniesList.forEach((c, idx) => {
      if (companySelect) {
        companySelect.innerHTML += `<option value="${c.id}">${c.companyName}</option>`;
      }
      const tr = document.createElement('tr');
      tr.innerHTML = `
        <td>${idx + 1}</td>
        <td><strong>${c.companyName}</strong></td>
        <td>${c.location || 'N/A'}</td>
        <td><a href="${c.website}" target="_blank" class="text-decoration-none">${c.website || 'N/A'}</a></td>
        <td><small class="text-muted">${c.description || ''}</small></td>
        <td>
          <button class="btn btn-sm btn-outline-danger" onclick="deleteCompany(${c.id})">Delete</button>
        </td>
      `;
      tbody.appendChild(tr);
    });
  } catch (err) {
    console.error("Failed to load companies:", err);
  }
}

async function handleSaveCompany(e) {
  e.preventDefault();
  const companyData = {
    companyName: document.getElementById('companyName').value,
    location: document.getElementById('companyLocation').value,
    website: document.getElementById('companyWebsite').value,
    description: document.getElementById('companyDescription').value,
  };

  try {
    await ApiClient.post('/companies', companyData);
    bootstrap.Modal.getInstance(document.getElementById('addCompanyModal'))?.hide();
    document.getElementById('saveCompanyForm').reset();
    await loadCompanies();
    await loadDashboardStats();
  } catch (err) {
    alert("Error adding company: " + err.message);
  }
}

async function deleteCompany(id) {
  if (!confirm("Are you sure you want to delete this company?")) return;
  try {
    await ApiClient.delete(`/companies/${id}`);
    await loadCompanies();
    await loadDashboardStats();
  } catch (err) {
    alert("Delete failed: " + err.message);
  }
}

/* ================= PLACEMENT DRIVES ================= */
async function loadPlacementDrives() {
  const tbody = document.getElementById('drivesTableBody');
  const filterSelect = document.getElementById('driveFilterSelect');
  if (!tbody) return;

  try {
    allDrivesList = await ApiClient.get('/drives');
    tbody.innerHTML = '';
    if (filterSelect) filterSelect.innerHTML = '<option value="ALL">All Drives</option>';

    if (!allDrivesList || allDrivesList.length === 0) {
      tbody.innerHTML = `<tr><td colspan="9" class="text-center text-muted py-3">No placement drives created yet.</td></tr>`;
      return;
    }

    allDrivesList.forEach((d, idx) => {
      if (filterSelect) {
        filterSelect.innerHTML += `<option value="${d.id}">${d.company?.companyName} - ${d.jobRole}</option>`;
      }
      const badgeClass = `badge-${d.status}`;
      const tr = document.createElement('tr');
      tr.innerHTML = `
        <td>${idx + 1}</td>
        <td><strong>${d.company?.companyName || 'N/A'}</strong></td>
        <td>${d.jobRole}</td>
        <td><span class="badge bg-light text-dark fw-bold">${d.packageAmount || 'N/A'}</span></td>
        <td>CGPA ≥ ${d.minCgpa}<br><small class="text-muted">Max Backlogs: ${d.maxBacklogs}</small></td>
        <td>${d.allowedBranches || 'All'}</td>
        <td>${d.deadline || 'N/A'}</td>
        <td><span class="badge badge-status ${badgeClass}">${d.status}</span></td>
        <td>
          <button class="btn btn-sm btn-outline-danger" onclick="deleteDrive(${d.id})">Delete</button>
        </td>
      `;
      tbody.appendChild(tr);
    });
  } catch (err) {
    console.error("Failed to load drives:", err);
  }
}

async function handleSaveDrive(e) {
  e.preventDefault();
  const driveData = {
    companyId: parseInt(document.getElementById('driveCompanySelect').value),
    jobRole: document.getElementById('driveJobRole').value,
    packageAmount: document.getElementById('drivePackage').value,
    minCgpa: parseFloat(document.getElementById('driveMinCgpa').value) || 0.0,
    maxBacklogs: parseInt(document.getElementById('driveMaxBacklogs').value) || 0,
    allowedBranches: document.getElementById('driveAllowedBranches').value,
    location: document.getElementById('driveLocation').value,
    deadline: document.getElementById('driveDeadline').value,
    driveDate: document.getElementById('driveDate').value,
    status: document.getElementById('driveStatus').value,
  };

  try {
    await ApiClient.post('/drives', driveData);
    bootstrap.Modal.getInstance(document.getElementById('addDriveModal'))?.hide();
    document.getElementById('saveDriveForm').reset();
    await loadPlacementDrives();
    await loadDashboardStats();
  } catch (err) {
    alert("Error creating drive: " + err.message);
  }
}

async function deleteDrive(id) {
  if (!confirm("Are you sure you want to delete this placement drive?")) return;
  try {
    await ApiClient.delete(`/drives/${id}`);
    await loadPlacementDrives();
    await loadDashboardStats();
  } catch (err) {
    alert("Delete failed: " + err.message);
  }
}

/* ================= STUDENTS ================= */
async function loadStudents() {
  const tbody = document.getElementById('studentsTableBody');
  if (!tbody) return;

  try {
    const students = await ApiClient.get('/students');
    tbody.innerHTML = '';

    if (!students || students.length === 0) {
      tbody.innerHTML = `<tr><td colspan="9" class="text-center text-muted py-3">No students registered yet.</td></tr>`;
      return;
    }

    students.forEach((s, idx) => {
      const tr = document.createElement('tr');
      tr.innerHTML = `
        <td>${idx + 1}</td>
        <td><strong>${s.s_name}</strong><br><small class="text-muted">${s.s_uid}</small></td>
        <td>${s.s_email}</td>
        <td>${s.branch || s.department || 'N/A'}</td>
        <td><span class="badge bg-primary">${s.cgpa !== null ? s.cgpa.toFixed(2) : '0.00'}</span></td>
        <td><span class="badge ${s.backlogs > 0 ? 'bg-danger' : 'bg-success'}">${s.backlogs}</span></td>
        <td>${s.graduationYear || 'N/A'}</td>
        <td><small>${s.skills || 'N/A'}</small></td>
        <td>
          <button class="btn btn-sm btn-outline-danger" onclick="deleteStudent(${s.s_id})">Delete</button>
        </td>
      `;
      tbody.appendChild(tr);
    });
  } catch (err) {
    console.error("Failed to load students:", err);
  }
}

async function deleteStudent(id) {
  if (!confirm("Are you sure you want to delete this student account?")) return;
  try {
    await ApiClient.delete(`/students/${id}`);
    await loadStudents();
    await loadDashboardStats();
  } catch (err) {
    alert("Delete failed: " + err.message);
  }
}

/* ================= APPLICATIONS & SHORTLISTING MATRIX ================= */
async function loadApplications() {
  const tbody = document.getElementById('applicationsTableBody');
  if (!tbody) return;

  try {
    const filterDriveId = document.getElementById('driveFilterSelect')?.value;
    let apps = await ApiClient.get('/applications');

    if (filterDriveId && filterDriveId !== 'ALL') {
      apps = apps.filter(a => a.placementDrive?.id === parseInt(filterDriveId));
    }

    tbody.innerHTML = '';

    if (!apps || apps.length === 0) {
      tbody.innerHTML = `<tr><td colspan="8" class="text-center text-muted py-3">No applications found for selected drive.</td></tr>`;
      return;
    }

    apps.forEach((app, idx) => {
      const student = app.student;
      const drive = app.placementDrive;
      const company = drive?.company;

      const tr = document.createElement('tr');
      tr.innerHTML = `
        <td>${idx + 1}</td>
        <td><strong>${student?.s_name || 'N/A'}</strong><br><small class="text-muted">${student?.s_uid} | ${student?.branch || student?.department}</small></td>
        <td>CGPA: <strong>${student?.cgpa || '0.0'}</strong> | Backlogs: ${student?.backlogs || 0}</td>
        <td><strong>${company?.companyName}</strong><br><small>${drive?.jobRole}</small></td>
        <td>${new Date(app.appliedDate).toLocaleDateString()}</td>
        <td><span class="badge badge-status badge-${app.status}">${app.status}</span></td>
        <td>
          <select class="form-select form-select-sm" onchange="updateAppStatus(${app.id}, this.value)">
            <option value="APPLIED" ${app.status === 'APPLIED' ? 'selected' : ''}>APPLIED</option>
            <option value="SHORTLISTED" ${app.status === 'SHORTLISTED' ? 'selected' : ''}>SHORTLISTED</option>
            <option value="INTERVIEW" ${app.status === 'INTERVIEW' ? 'selected' : ''}>INTERVIEW</option>
            <option value="SELECTED" ${app.status === 'SELECTED' ? 'selected' : ''}>SELECTED</option>
            <option value="REJECTED" ${app.status === 'REJECTED' ? 'selected' : ''}>REJECTED</option>
          </select>
        </td>
      `;
      tbody.appendChild(tr);
    });
  } catch (err) {
    console.error("Failed to load applications:", err);
  }
}

async function updateAppStatus(appId, newStatus) {
  try {
    await ApiClient.put(`/applications/${appId}/status`, { status: newStatus });
    await loadApplications();
    await loadDashboardStats();
  } catch (err) {
    alert("Status update failed: " + err.message);
  }
}
