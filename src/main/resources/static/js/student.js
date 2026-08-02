/* Student Dashboard Controller */

document.addEventListener('DOMContentLoaded', async () => {
  AuthManager.requireAuth('STUDENT', '/index.html');
  const user = AuthManager.getCurrentUser();

  // Set Student Header Name & ID
  document.getElementById('studentNavName').textContent = user.name || 'Student';

  await loadStudentProfile(user.id);
  await loadPlacementDrives(user.id);
  await loadMyApplications(user.id);

  // Profile Form Event Listener
  const profileForm = document.getElementById('profileForm');
  if (profileForm) {
    profileForm.addEventListener('submit', async (e) => {
      e.preventDefault();
      await updateProfile(user.id);
    });
  }

  // Logout Listener
  document.getElementById('logoutBtn')?.addEventListener('click', (e) => {
    e.preventDefault();
    AuthManager.logout();
  });
});

let currentStudent = null;
let activeDrivesMap = new Map();

async function loadStudentProfile(studentId) {
  try {
    currentStudent = await ApiClient.get(`/students/${studentId}`);
    
    // Fill display elements
    document.getElementById('displayStudentName').textContent = currentStudent.s_name || 'N/A';
    document.getElementById('displayStudentUid').textContent = currentStudent.s_uid || 'N/A';
    document.getElementById('displayStudentBranch').textContent = currentStudent.branch || currentStudent.department || 'N/A';
    document.getElementById('displayStudentCgpa').textContent = currentStudent.cgpa !== null ? currentStudent.cgpa.toFixed(2) : '0.00';
    document.getElementById('displayStudentBacklogs').textContent = currentStudent.backlogs !== null ? currentStudent.backlogs : '0';
    document.getElementById('displayStudentGradYear').textContent = currentStudent.graduationYear || 'N/A';
    document.getElementById('displayStudentSkills').textContent = currentStudent.skills || 'No skills listed yet';

    // Populate Edit Form
    document.getElementById('editName').value = currentStudent.s_name || '';
    document.getElementById('editPhone').value = currentStudent.phone || '';
    document.getElementById('editCity').value = currentStudent.city || '';
    document.getElementById('editBranch').value = currentStudent.branch || currentStudent.department || '';
    document.getElementById('editCgpa').value = currentStudent.cgpa || '';
    document.getElementById('editBacklogs').value = currentStudent.backlogs !== undefined ? currentStudent.backlogs : 0;
    document.getElementById('editGradYear').value = currentStudent.graduationYear || '';
    document.getElementById('editSkills').value = currentStudent.skills || '';
  } catch (err) {
    console.error("Failed to load profile:", err);
    showAlert('profileAlert', 'danger', 'Failed to load profile data');
  }
}

async function updateProfile(studentId) {
  const updatedData = {
    s_name: document.getElementById('editName').value,
    phone: document.getElementById('editPhone').value,
    city: document.getElementById('editCity').value,
    branch: document.getElementById('editBranch').value,
    department: document.getElementById('editBranch').value,
    cgpa: parseFloat(document.getElementById('editCgpa').value) || 0.0,
    backlogs: parseInt(document.getElementById('editBacklogs').value) || 0,
    graduationYear: parseInt(document.getElementById('editGradYear').value) || null,
    skills: document.getElementById('editSkills').value,
  };

  try {
    const updated = await ApiClient.put(`/students/${studentId}`, updatedData);
    showAlert('profileAlert', 'success', 'Profile updated successfully!');
    bootstrap.Modal.getInstance(document.getElementById('editProfileModal'))?.hide();
    await loadStudentProfile(studentId);
    await loadPlacementDrives(studentId);
  } catch (err) {
    showAlert('profileAlert', 'danger', 'Update failed: ' + err.message);
  }
}

async function loadPlacementDrives(studentId) {
  const drivesContainer = document.getElementById('drivesContainer');
  if (!drivesContainer) return;

  try {
    const drives = await ApiClient.get('/drives');
    drivesContainer.innerHTML = '';
    activeDrivesMap.clear();

    if (!drives || drives.length === 0) {
      drivesContainer.innerHTML = `<div class="col-12 text-center text-muted py-4">No active placement drives currently posted.</div>`;
      return;
    }

    for (const drive of drives) {
      activeDrivesMap.set(drive.id, drive);
      // Fetch eligibility
      const eligibility = await ApiClient.get(`/drives/${drive.id}/eligibility/${studentId}`);

      const col = document.createElement('div');
      col.className = 'col-md-6 col-lg-4 mb-4';

      const isEligible = eligibility.eligible;
      const badgeClass = isEligible ? 'badge-eligible' : 'badge-ineligible';
      const badgeText = isEligible ? 'Eligible to Apply' : 'Ineligible';

      col.innerHTML = `
        <div class="card h-100 custom-card shadow-sm">
          <div class="card-body">
            <div class="d-flex justify-content-between align-items-start mb-2">
              <h5 class="card-title text-primary font-weight-bold mb-0">${drive.company?.companyName || 'Company'}</h5>
              <span class="badge ${badgeClass}">${badgeText}</span>
            </div>
            <h6 class="card-subtitle mb-2 text-muted fw-semibold">${drive.jobRole}</h6>
            <div class="mb-3">
              <div class="small text-dark mb-1"><i class="bi bi-currency-dollar text-success"></i> <strong>Package:</strong> ${drive.packageAmount || 'N/A'}</div>
              <div class="small text-dark mb-1"><i class="bi bi-geo-alt text-danger"></i> <strong>Location:</strong> ${drive.location || 'N/A'}</div>
              <div class="small text-muted mb-1"><strong>Min CGPA:</strong> ${drive.minCgpa} | <strong>Max Backlogs:</strong> ${drive.maxBacklogs}</div>
              <div class="small text-muted"><strong>Allowed Branches:</strong> ${drive.allowedBranches || 'All'}</div>
            </div>
            <div class="border-top pt-2 d-flex justify-content-between align-items-center">
              <small class="text-muted">Deadline: ${drive.deadline || 'N/A'}</small>
              <button class="btn btn-sm ${isEligible ? 'btn-primary' : 'btn-outline-secondary'}" 
                      onclick="openApplyModal(${drive.id}, ${isEligible})">
                ${isEligible ? 'Apply Now' : 'Details'}
              </button>
            </div>
          </div>
        </div>
      `;
      drivesContainer.appendChild(col);
    }
  } catch (err) {
    console.error("Failed to load drives:", err);
    drivesContainer.innerHTML = `<div class="col-12 text-center text-danger py-4">Error loading placement drives.</div>`;
  }
}

async function openApplyModal(driveId, isEligible) {
  const drive = activeDrivesMap.get(driveId);
  if (!drive) return;

  const user = AuthManager.getCurrentUser();
  const eligibility = await ApiClient.get(`/drives/${driveId}/eligibility/${user.id}`);

  document.getElementById('applyDriveId').value = drive.id;
  document.getElementById('applyCompanyTitle').textContent = `${drive.company?.companyName} - ${drive.jobRole}`;
  
  const reasonsDiv = document.getElementById('eligibilityReasons');
  if (eligibility.eligible) {
    reasonsDiv.className = 'alert alert-success';
    reasonsDiv.innerHTML = `<strong>Congratulations!</strong> You satisfy all criteria for this drive.`;
    document.getElementById('confirmApplyBtn').disabled = false;
  } else {
    reasonsDiv.className = 'alert alert-warning';
    reasonsDiv.innerHTML = `<strong>Ineligibility Notice:</strong><ul class="mb-0 ps-3">${eligibility.reasons.map(r => `<li>${r}</li>`).join('')}</ul>`;
    document.getElementById('confirmApplyBtn').disabled = true;
  }

  const modal = new bootstrap.Modal(document.getElementById('applyModal'));
  modal.show();
}

async function submitApplication() {
  const driveId = document.getElementById('applyDriveId').value;
  const user = AuthManager.getCurrentUser();

  try {
    await ApiClient.post(`/applications/apply?studentId=${user.id}&driveId=${driveId}`);
    showAlert('dashboardAlert', 'success', 'Application submitted successfully!');
    bootstrap.Modal.getInstance(document.getElementById('applyModal'))?.hide();
    await loadMyApplications(user.id);
  } catch (err) {
    alert("Application failed: " + err.message);
  }
}

async function loadMyApplications(studentId) {
  const tableBody = document.getElementById('applicationsTableBody');
  if (!tableBody) return;

  try {
    const apps = await ApiClient.get(`/applications/student/${studentId}`);
    tableBody.innerHTML = '';

    if (!apps || apps.length === 0) {
      tableBody.innerHTML = `<tr><td colspan="5" class="text-center text-muted py-3">You have not applied to any drives yet.</td></tr>`;
      return;
    }

    apps.forEach((app, index) => {
      const drive = app.placementDrive;
      const company = drive?.company;
      const statusClass = `badge-${app.status}`;
      const formattedDate = new Date(app.appliedDate).toLocaleDateString();

      const tr = document.createElement('tr');
      tr.innerHTML = `
        <td>${index + 1}</td>
        <td><strong>${company?.companyName || 'N/A'}</strong></td>
        <td>${drive?.jobRole || 'N/A'} (${drive?.packageAmount || ''})</td>
        <td>${formattedDate}</td>
        <td><span class="badge badge-status ${statusClass}">${app.status}</span></td>
      `;
      tableBody.appendChild(tr);
    });
  } catch (err) {
    console.error("Failed to load applications:", err);
  }
}

function showAlert(elementId, type, message) {
  const container = document.getElementById(elementId);
  if (!container) return;
  container.innerHTML = `<div class="alert alert-${type} alert-dismissible fade show" role="alert">
    ${message}
    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
  </div>`;
}
