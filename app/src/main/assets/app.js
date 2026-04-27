// Giới hạn TP.HCM (bounding box)
const HCM_BOUNDS = [
  [10.3, 106.3],
  [11.2, 107.1]
];

// Tạo map + khóa phạm vi
const map = L.map('map', {
  maxBounds: HCM_BOUNDS,
  maxBoundsViscosity: 1.0
}).setView([10.8231, 106.6297], 13);

// Tile (tiếng Việt tương đối)
L.tileLayer('https://{s}.tile.openstreetmap.fr/hot/{z}/{x}/{y}.png', {
  maxZoom: 19,
}).addTo(map);

// Giới hạn zoom
map.setMinZoom(10);
map.setMaxZoom(18);

let routeLine;
let markerFrom;
let markerTo;

// Kiểm tra có nằm trong HCM không
function isInHCM(lat, lon) {
  return lat >= 10.3 && lat <= 11.2 &&
         lon >= 106.3 && lon <= 107.1;
}

// Lấy tọa độ (chỉ trong HCM)
async function getCoords(place) {
  const res = await fetch(
    `https://nominatim.openstreetmap.org/search?format=json&accept-language=vi&bounded=1&viewbox=106.3,11.2,107.1,10.3&q=${place}`
  );

  const data = await res.json();
  if (data.length === 0) return null;

  return {
    lat: parseFloat(data[0].lat),
    lon: parseFloat(data[0].lon)
  };
}

// Tìm đường
async function findRoute() {
  const from = document.getElementById("from").value;
  const to = document.getElementById("to").value;

  const start = await getCoords(from);
  const end = await getCoords(to);

  if (!start || !end) {
    alert("❌ Không tìm thấy địa điểm!");
    return;
  }

  if (!isInHCM(start.lat, start.lon) || !isInHCM(end.lat, end.lon)) {
    alert("❌ Chỉ được chọn địa điểm trong TP.HCM!");
    return;
  }

  if (markerFrom) map.removeLayer(markerFrom);
  if (markerTo) map.removeLayer(markerTo);

  markerFrom = L.marker([start.lat, start.lon]).addTo(map).bindPopup("📍 Điểm đi").openPopup();
  markerTo = L.marker([end.lat, end.lon]).addTo(map).bindPopup("📍 Điểm đến");

  const url = `https://router.project-osrm.org/route/v1/driving/${start.lon},${start.lat};${end.lon},${end.lat}?overview=full&geometries=geojson`;

  const res = await fetch(url);
  const data = await res.json();

  const coords = data.routes[0].geometry.coordinates;
  const latlngs = coords.map(c => [c[1], c[0]]);

  if (routeLine) map.removeLayer(routeLine);

  routeLine = L.polyline(latlngs, {
    color: 'blue',
    weight: 5
  }).addTo(map);

  map.fitBounds(routeLine.getBounds());
}
